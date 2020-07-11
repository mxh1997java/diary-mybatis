package www.maxinhai.com.diarymybatis.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.config.rabbitmq.Producer;
import www.maxinhai.com.diarymybatis.entity.Order;
import www.maxinhai.com.diarymybatis.entity.Product;
import www.maxinhai.com.diarymybatis.mapper.OrderMapper;
import www.maxinhai.com.diarymybatis.mapper.ProductMapper;
import www.maxinhai.com.diarymybatis.service.SecondKillService;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.DateUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 秒杀业务
 * @Param:
 * @Return:
 * @Author: 15735400536
 * @Date: 2020/7/11 23:02
 */
@Service
public class SecondKillServiceImpl implements SecondKillService {

    private Logger logger = LoggerFactory.getLogger(SecondKillServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private Producer producer;

    /**
     * 功能描述: 添加商品数据
     * @Param: [product]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 19:48
     */
    @Override
    public int addProductInfo(Product product) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(product), "缺少必要参数!");
        product.setCreateTime(new Date());
        return productMapper.addProduct(product);
    }


    /**
     * 功能描述: 初始化要秒杀的商品数据
     * @Param: [productId]
     * @Return: boolean
     * @Author: 15735400536
     * @Date: 2020/7/11 19:47
     */
    @Override
    public boolean intiProductData(Long productId) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(productId), "缺少必要参数!");
        Map<String, Object> condition = new HashMap<>();
        condition.put("productId", productId);
        Product product = productMapper.findOneByCondition(condition);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(product), "该数据不存在!");
        String productName = product.getProductName();
        Integer productNum = product.getProductNum();
        //将商品秒杀数量放入redis
        redisTemplate.opsForValue().set(productName, productNum);
        redisTemplate.expire(productName, 60*60, TimeUnit.MINUTES);
        return true;
    }


    /**
     * 功能描述: 秒杀商品
     * @Param: [params]
     * @Return: boolean
     * @Author: 15735400536
     * @Date: 2020/7/11 23:01
     */
    @Override
    public boolean secondKill(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "缺少必要参数!");
        String productId = params.get("productId").toString();
        AssertUtils.assertTrue(EmptyUtils.isEmpty(productId), "缺少productId!");
        String productName = params.get("productName").toString();
        AssertUtils.assertTrue(EmptyUtils.isEmpty(productName), "缺少productName!");
        String username = params.get("username").toString();
        AssertUtils.assertTrue(EmptyUtils.isEmpty(username), "缺少username!");

        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();

        Object data = opsForValue.get(productName);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(data), "该商品不再秒杀活动中!");

        String key = productName + "_lock";
        Long num = Long.valueOf(opsForValue.get(productName).toString());
        if(num == 0) {
            logger.info("第一步 商品: {} 数量: {}", productName, num);
            return false;
        }
        //获取redis分布式锁，创建成功获取锁
        if(opsForValue.setIfAbsent(key, "DISTRIBUTED_LOCK")) {
            //防止死锁，设置分布式锁存活时间
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
            Long newNum = Long.valueOf(opsForValue.get(productName).toString());
            if(newNum == 0) {
                logger.info("第二步 商品: {} 数量: {}", productName, num);
                return false;
            }
            newNum--;
            opsForValue.set(productName, newNum);
            producer.send("恭喜:" + username + "秒杀" + productName);
            logger.info("恭喜: {} 秒杀 {}", username, productName);
            //修改完数据，删除分布式锁
            if(redisTemplate.delete(key)) {
                logger.info("线程: {} 删除 {}", Thread.currentThread(), key);
            }
        }
        return true;
    }


    /**
     * 功能描述: 用户删除购物车订单信息
     * @Param: [productName, orderNo]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 23:18
     */
    @Override
    public int removeOrder(String productName, String orderNo) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(productName), "缺少productName!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(orderNo), "缺少orderNo!");
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        Long size = opsForList.size(productName);
        if(size <= 0) {
            return 0;
        }
        Order order = new Order();
        order.setOrderNo(orderNo);
        opsForList.remove(productName, 1, order);
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //获取redis分布式锁，创建成功获取锁
        String key = productName + "_lock";
        if(opsForValue.setIfAbsent(key, "DISTRIBUTED_LOCK")) {
            //防止死锁，设置分布式锁存活时间
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
            opsForValue.increment(key, 1);
            //修改完数据，删除分布式锁
            if(redisTemplate.delete(key)) {
                logger.info("线程: {} 删除 {}", Thread.currentThread(), key);
            }
        }
        return 1;
    }


    /**
     * 功能描述: 创建订单到购物车
     * @Param: [order]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 23:30
     */
    @Override
    public int createOrder(Order order) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(order), "缺少必要参数!");
        Date newTime = new Date();
        order.setCreateTime(newTime);
        // 状态100 出单状态但未支付
        order.setOrderState(100);
        //有效时间30分钟
        order.setMaxTime(30);
        Date invalidTime = DateUtils.calculationDate(newTime, Calendar.MINUTE, 30);
        order.setInvalidTime(invalidTime);
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        //将订单信息放入用户购物车
        opsForList.rightPush(order.getUsername() + ":ShoppingCat", order);
        return 1;
    }

    /**
     * 功能描述: 将redis订单数据批量导入mysql
     * @Param: [orderList]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 23:01
     */
    @Override
    public int batchImportData(List<Order> orderList) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(orderList), "缺少必要参数!");
        return orderMapper.addOrderBatch(orderList);
    }

}
