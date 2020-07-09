package www.maxinhai.com.diarymybatis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    String REDIS_HOST;

    @Value("${spring.redis.port}")
    String REDIS_PORT;

    @Bean
    public RedissonClient getClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
