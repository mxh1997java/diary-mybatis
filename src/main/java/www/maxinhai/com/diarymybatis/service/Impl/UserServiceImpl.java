package www.maxinhai.com.diarymybatis.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
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
