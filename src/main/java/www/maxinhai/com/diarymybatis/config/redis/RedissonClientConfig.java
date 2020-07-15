package www.maxinhai.com.diarymybatis.config.redis;

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
public class RedissonClientConfig {

    @Value("${spring.redis.host}")
    String REDIS_HOST;

    @Value("${spring.redis.port}")
    String REDIS_PORT;

    /**
     * 会报错:
     * 2020-07-11 21:46:24.127 ERROR 2172 --- [isson-netty-4-2] o.redisson.client.handler.CommandsQueue  :
     * Exception occured. Channel: [id: 0x667b2b6d, L:/127.0.0.1:52780 - R:127.0.0.1/127.0.0.1:6379]
     *
     * java.io.IOException: 远程主机强迫关闭了一个现有的连接。
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient getClient(){
        Config config = new Config();
        config.setNettyThreads(2);
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(1)
                .setRetryAttempts(1)
                .setRetryInterval(0)
                .setPingTimeout(2000)
                .setTimeout(5000)
                .setClientName("DefaultSyncStateCacheClient");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
