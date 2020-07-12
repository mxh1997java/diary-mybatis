package www.maxinhai.com.diarymybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import www.maxinhai.com.diarymybatis.config.RedissonClientConfig;
import www.maxinhai.com.diarymybatis.config.rabbitmq.Producer;
import www.maxinhai.com.diarymybatis.entity.Product;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.util.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiaryMybatisApplicationTests {

    /**
     * excel工具类测试
     */
    @Test
    public void excelUtilsTest() {

        try {
            List<List<Object>> list = ExcelUtils.readExcelFirstSheet("D:\\Users\\15735400536\\Downloads\\考勤-马鑫海-3月.xlsx");
            list.forEach(item -> {
                item.forEach(element -> {
                    System.out.print(element);
                });
                System.out.println("");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 统一返回封装测试
     */
    @Test
    public void ResponseTest() {
        BaseResponse out = BaseResponse.out(CodeEnum.SUCCESS);
        System.out.println("基本响应封装:\n" + JSON.toJSONString(out));
        List<String> list = new LinkedList<>();
        list.add(UUID.randomUUID().toString());
        ResponseData<List<String>> data = ResponseData.out(CodeEnum.FAIL, list);
        System.out.println("响应数据结构封装:\n" + JSON.toJSONString(data));
    }


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    /**
     * Redis工具类测试
     */
    @Test
    public void redisUtilsTest() {
        redisUtils.setRedisTemplate(redisTemplate);
        User user = new User();
        user.setUser_id(1l);
        user.setUsername("maxinhai");
        user.setPassword("maxinhai");
        redisUtils.set("user", user);
        User user1 = (User)redisUtils.get("user");
        System.out.println(user1);

        List<User> userList = new ArrayList<>();
        for(long i=0; i<100; i++) {
            User user2 = new User();
            user2.setUser_id(i);
            user2.setUsername("maxinhai" + i);
            user2.setPassword("maxinhai" + i);
            userList.add(user2);
        }
        boolean result = redisUtils.lSet("userList", userList);
        List<Object> users = redisUtils.lGet("userList", 1, 100);
        System.out.println("redis users" + users);

        Map<String, Object> map = new HashMap<>();
        map.put("1", user);
        map.put("2", user);
        map.put("3", user);
        result = redisUtils.hmset("map", map);
        Map<Object, Object> map1 = redisUtils.hmget("map");
        System.out.println("redis map" + map1);

    }

    /**
     * 十万根线程自增count
     */
    @Test
    public void incrCount() {
        CountDownLatch countDownLatch = new CountDownLatch(100000);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        ExecutorService threadPool = ThreadPoolUtils.getThreadPool();
        redisUtils.setRedisTemplate(redisTemplate);
        redisUtils.set("count", 0);
        for(int i=0; i<100000; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    writeLock.lock();
                    try {
                        Object value = redisTemplate.opsForValue().get("count");
                        long count = Long.valueOf(String.valueOf(value));
                        count++;
                        redisTemplate.opsForValue().set("count", count);
                        //redisUtils.set("count", count);
                        System.out.println(Thread.currentThread().getName() + " 运算count结果: " + redisUtils.get("count"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock.unlock();
                    }
                }
            });
            countDownLatch.countDown();
        }

        String str = String.valueOf(redisTemplate.opsForValue().get("count"));
        while (!str.equals("100000")) {
            str = String.valueOf(redisTemplate.opsForValue().get("count"));
        }
    }


    @Test
    public void zsetTest() {
        Set<String> set = new LinkedHashSet<>();
        set.add("maxinhai");
        set.add("maxinhai2");
        set.add("maxinhai1");
        redisTemplate.opsForZSet().add("zset", set, 1);
    }


    @Test
    public void booleanFilterTest() {
        int size = 1000000;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        long startTime = System.nanoTime(); // 获取开始时间
        //判断这一百万个数中是否包含29999这个数
        if (bloomFilter.mightContain(29999)) {
            System.out.println("命中了");
        }
        long endTime = System.nanoTime();   // 获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "纳秒");
    }


    /**
     * 测试布隆过滤器误判率
     */
    @Test
    public void booleanFilterTest1() {
        int size = 1000000;

        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.01);

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList<Integer>(1000);
        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判的数量：" + list.size());
    }


    @Autowired
    DistributedLockUtils distributedLock;

    /**
     * 分布式锁测试
     */
    @Test
    public void lockTest() {
        String LOCK_ID = "HelloWorld";
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for(int i=0; i<10; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean lock = distributedLock.getLock(LOCK_ID, 10 * 1000);
                if (lock) {
                    System.out.println(Thread.currentThread().getName() + "执行任务");
                    distributedLock.releaseLock(LOCK_ID);
                } else {
                    System.out.println(Thread.currentThread().getName() + "没有抢到锁");
                }
            }).start();
            countDownLatch.countDown();
        }

        try {
            //主线程休眠5秒
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    RedissonClientConfig redissonClientConfig;

    /**
     * 测试redisson客户端分布式锁
     */
    @Test
    public void lockTest1() {
        String LOCK_ID = "HelloWorld";
        CountDownLatch countDownLatch = new CountDownLatch(10);
        RedissonClient client = redissonClientConfig.getClient();

        for(int i=0; i<10; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RLock lock = client.getLock(LOCK_ID);

                // 1. 最常见的使用方法
                //lock.lock();
                // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
                //lock.lock(10, TimeUnit.SECONDS);
                // 3. 尝试加锁，最多等待2秒，上锁以后8秒自动解锁
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "执行任务");

            }).start();
            countDownLatch.countDown();
        }

        try {
            //主线程休眠5秒
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Value("${redis_user_key}")
    String redis_user_key;

    /**
     * 读取配置文件测试
     */
    @Test
    public void readProperties() {
        System.out.println(redis_user_key);
    }

    @Autowired
    private Producer producer;


    /**
     * 功能描述: 消息队列测试
     * @Param: []
     * @Return: void
     * @Author: 15735400536
     * @Date: 2020/7/11 20:58
     */
    @Test
    public void testRabbitMQ() {
        ExecutorService threadPool = ThreadPoolUtils.getThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0; i<10; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    producer.send("message: " + UUID.randomUUID());
                }
            });
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void objectToJSONObject() {
        List<Object> objectList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Product product = new Product();
            product.setProductId(1l);
            product.setProductName("name" + i);
            objectList.add(product);
        }
        String jsonString = JSONArray.toJSONString(objectList);
        List<Product> products = JSONObject.parseArray(jsonString, Product.class);
        products.forEach(item -> {
            System.out.println(item.toString());
        });
    }

}
