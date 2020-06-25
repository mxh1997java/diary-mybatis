package www.maxinhai.com.diarymybatis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://122.51.248.77:6379");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
