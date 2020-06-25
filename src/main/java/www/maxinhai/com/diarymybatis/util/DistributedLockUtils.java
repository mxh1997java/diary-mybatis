package www.maxinhai.com.diarymybatis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁
 */
@Component
public class DistributedLockUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 获得锁
     */
    public boolean getLock(String lockId, long millisecond) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, "lock");
        redisTemplate.expire(lockId, millisecond, TimeUnit.MILLISECONDS);
        return success != null && success;
    }


    /**
     * 释放锁
     * @param lockId
     */
    public void releaseLock(String lockId) {
        redisTemplate.delete(lockId);
    }

}
