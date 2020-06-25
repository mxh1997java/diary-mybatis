package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.LoginInfoServce;
import java.util.List;
import java.util.Map;

@Service
public class LoginInfoServiceImpl extends AbstractService implements LoginInfoServce {

    @Override
    public int addLoginInfo(LoginInfo info) {
        return loginInfoMapper.addLoginInfo(info);
    }

    @Override
    public List<LoginInfo> findAllByCondition(Map<String, Object> params) {
        return loginInfoMapper.findAllByCondition(params);
    }
}
