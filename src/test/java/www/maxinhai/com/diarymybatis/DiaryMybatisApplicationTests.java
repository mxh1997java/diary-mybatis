package www.maxinhai.com.diarymybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.util.*;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.*;

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

}
