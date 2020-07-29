package www.maxinhai.com.diarymybatis.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.maxinhai.com.diarymybatis.entity.RegisteredInfo;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.UserService;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserServiceImpl extends AbstractService implements UserService {

    //rollbackFor会回滚 noRollbackFor不会回滚
    @Transactional(rollbackFor=RuntimeException.class, noRollbackFor=Exception.class)
    @Override
    public int addUser(User user) throws Exception {
        AssertUtils.assertFalse(null != user.getUsername(), "用户名不为空!");
        AssertUtils.assertFalse(null != user.getPassword(), "密码不为空!");
        User findResult = userMapper.findOneByCondition(user);
        AssertUtils.assertTrue(findResult != null, "用户名" + user.getUsername() + "已存在!");
        user.setCreateTime(new Date());
        RegisteredInfo registeredInfo = new RegisteredInfo();
        registeredInfo.setCreator("admin");
        registeredInfo.setRegisterTime(new Date());
        registeredInfo.setUsername(user.getUsername());
        int result = registeredInfoMapper.addRegisteredInfo(registeredInfo);
        AssertUtils.assertTrue(result < 1, "添加用户注册信息出错!");
        result = userMapper.addUser(user);
        AssertUtils.assertTrue(result < 1, "创建账户出错!");
        return result;
    }

    @Override
    public int delUser(Long id) throws Exception {
        AssertUtils.assertFalse(null != id, "id不为空!");
        return userMapper.deleteUserById(id);
    }

    @Override
    public int modifyUser(Map<String, Object> params) throws Exception {
        AssertUtils.assertFalse(null != params.get("user_id"), "id不为空!");
        User user = new User();
        user.setUser_id(Long.valueOf(String.valueOf(params.get("user_id"))));
        User findResult = userMapper.findOneByCondition(user);
        AssertUtils.assertTrue(findResult == null, "修改数据不存在!");
        findResult.setModifyTime(new Date());
        if(null != params.get("username")) {
            findResult.setUsername(String.valueOf(params.get("username")));
        }
        if(null != params.get("password")) {
            findResult.setPassword(String.valueOf(params.get("password")));
        }
        if(null != params.get("description")) {
            findResult.setDescription(String.valueOf(params.get("description")));
        }
        return userMapper.updateUserById(findResult);
    }

    @Override
    public Map<String, Object> findAllByCondition(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "params不为空!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params.get("pageNum")), "pageNum不为空!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params.get("pageSize")), "pageSize不为空!");
        Integer pageNum = Integer.valueOf(String.valueOf(params.get("pageNum")));
        Integer pageSize = Integer.valueOf(String.valueOf(params.get("pageSize")));
        if(pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.findAllByCondition(params);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("code", 200);
        result.put("data", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        return result;
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
