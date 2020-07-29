package www.maxinhai.com.diarymybatis;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

}
