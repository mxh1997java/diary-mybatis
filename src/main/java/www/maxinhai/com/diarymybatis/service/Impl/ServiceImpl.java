package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.UserService;

@Transactional
@Service
public class ServiceImpl extends AbstractService implements UserService {

    @Override
    public void addUser(User user) {

    }

    @Override
    public void delUser(Long id) {

    }

    @Override
    public void modifyUser(User user) {

    }
}
