package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.UserService;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Override
    public int addUser(User user) throws Exception {
        AssertUtils.assertFalse(null != user.getUsername(), "用户名不为空!");
        AssertUtils.assertFalse(null != user.getPassword(), "密码不为空!");
        User findResult = userMapper.findOneByCondition(user);
        AssertUtils.assertFalse(EmptyUtils.isEmpty(findResult), "用户名" + user.getUsername() + "已存在!");
        user.setCreateTime(new Date());
        return userMapper.addUser(user);
    }

    @Override
    public int delUser(Long id) throws Exception {
        AssertUtils.assertFalse(null != id, "id不为空!");
        return userMapper.deleteUserById(id);
    }

    @Override
    public int modifyUser(User user) throws Exception {
        AssertUtils.assertFalse(null != user.getUser_id(), "id不为空!");
        User findResult = userMapper.findOneByCondition(user);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(findResult), "修改数据不存在!");
        user.setModifyTime(new Date());
        return userMapper.updateUserById(user);
    }

    @Override
    public List<User> findAllByCondition(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "params不为空!");
        return userMapper.findAllByCondition(params);
    }

    @Override
    public User findOneByCondition(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "params不为空!");
        User user = new User();
        if(!EmptyUtils.isEmpty(params.get("user_id"))) {
            user.setUser_id(Long.valueOf(String.valueOf(params.get("user_id"))));
        }
        if(!EmptyUtils.isEmpty(params.get("username"))) {
            user.setUsername(String.valueOf(params.get("username")));
        }
        return userMapper.findOneByCondition(user);
    }
}
