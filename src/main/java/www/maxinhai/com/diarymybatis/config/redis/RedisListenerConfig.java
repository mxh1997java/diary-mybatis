package www.maxinhai.com.diarymybatis.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 功能描述: redis监听容器
 * @Author: 15735400536
 * @Date: 2020/7/15 11:08
 */
@Configuration
public class RedisListenerConfig {

    @Bean
    public ChannelTopic expiredTopic() {
        return new ChannelTopic("__keyevent@0__:expired");  // 选择0号数据库
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}
