package www.maxinhai.com.diarymybatis;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import www.maxinhai.com.diarymybatis.entity.User;
import javax.annotation.PostConstruct;

@EnableHasor()
@EnableHasorWeb()
// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
//@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:8529", "http://127.0.0.1:8529"})
@ServletComponentScan   //使用过滤器时使用
@MapperScan("www.maxinhai.com.diarymybatis")
@SpringBootApplication
public class DiaryMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryMybatisApplication.class, args);
    }

    /**
     * 功能描述: 在spring容器初始化的时候执行该方法
     * @Param: []
     * @Return: void
     * @Author: 15735400536
     * @Date: 2020/8/9 15:46
     */
    @PostConstruct
    public void init() {
        System.out.println("初始化admin用户");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setUser_id(1l);
        user.setDescription("this is admin");
        user.setName("admin");
    }

}
