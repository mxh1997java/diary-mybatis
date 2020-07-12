package www.maxinhai.com.diarymybatis.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.entity.Order;
import www.maxinhai.com.diarymybatis.entity.Product;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.service.Impl.SecondKillServiceImpl;
import www.maxinhai.com.diarymybatis.service.Impl.UserServiceImpl;
import www.maxinhai.com.diarymybatis.util.DateUtils;
import www.maxinhai.com.diarymybatis.util.ThreadPoolUtils;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * 功能描述: 消息生产者
 * @Param:
 * @Return:
 * @Author: 15735400536
 * @Date: 2020/7/11 20:46
 */
@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecondKillServiceImpl secondKillService;

    private Logger log = LoggerFactory.getLogger(Producer.class);

    /**
     * 给hello队列发送消息
     */
    public void send(String message) {
        rabbitTemplate.convertAndSend("queue-secondKill", message);
        log.info("生产者发送消息: {}", message);
    }


    /**
     * 给hello队列发送消息
     */
    public void send(String username, String productName) throws Exception{
        ExecutorService threadPool = ThreadPoolUtils.getThreadPool();

        FutureTask<User> findOneUser = new FutureTask<User>(() -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("username", username);
            return userService.findOneByCondition(condition);
        });

        FutureTask<Product> findOneProduct = new FutureTask<Product>(() -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("productName", productName);
            return secondKillService.findOneProductInfo(condition);
        });
        threadPool.submit(findOneUser);
        threadPool.submit(findOneProduct);

        User user = findOneUser.get();
        Product product = findOneProduct.get();

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setUserId(user.getUser_id());
        order.setUsername(user.getUsername());
        order.setProductId(product.getProductId());
        order.setProductName(product.getProductName());
        order.setOrderState(100);
        // 失效时间
        order.setInvalidTime(DateUtils.calculationDate(new Date(), Calendar.MINUTE, 30));
        // 订单有效期
        order.setMaxTime(30);

        rabbitTemplate.convertAndSend("secondKill", order);
        log.info("生产者发送消息: {}", order);
    }

}
