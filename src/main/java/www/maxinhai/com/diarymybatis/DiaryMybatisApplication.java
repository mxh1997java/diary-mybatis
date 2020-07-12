package www.maxinhai.com.diarymybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:8529", "http://127.0.0.1:8529"})
@ServletComponentScan   //使用过滤器时使用
@MapperScan("www.maxinhai.com.diarymybatis")
@SpringBootApplication
public class DiaryMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryMybatisApplication.class, args);
    }

}
