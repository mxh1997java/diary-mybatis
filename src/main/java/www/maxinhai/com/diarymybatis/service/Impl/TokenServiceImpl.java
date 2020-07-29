package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.TokenService;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;

import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl extends AbstractService implements TokenService{

    @Override
    public int addToken(String key, Object token) throws Exception {
        redisTemplate.opsForValue().set(key, token, 2, TimeUnit.SECONDS);
        return 1;
    }

    @Override
    public int delToken(String key) throws Exception {
        redisTemplate.expire(key, -1, TimeUnit.SECONDS);
        return 1;
    }

    @Override
    public boolean checkToken(String key) throws Exception {
        Object o = redisTemplate.opsForValue().get(key);
        if(EmptyUtils.isEmpty(o)) {
            return true;
        }
        return false;
    }
}
