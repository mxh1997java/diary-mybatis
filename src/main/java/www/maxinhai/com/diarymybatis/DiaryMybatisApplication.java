package www.maxinhai.com.diarymybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("www.maxinhai.com.diarymybatis")
@SpringBootApplication
public class DiaryMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryMybatisApplication.class, args);
    }

}
