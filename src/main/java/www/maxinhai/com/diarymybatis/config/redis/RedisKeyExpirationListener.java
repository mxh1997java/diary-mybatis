package www.maxinhai.com.diarymybatis.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 功能描述: redis key 过期监听器
 * @Author: 15735400536
 * @Date: 2020/7/15 11:04
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        System.out.println("redis key过期: " + expiredKey);
        //业务逻辑处理。。。
    }

}
