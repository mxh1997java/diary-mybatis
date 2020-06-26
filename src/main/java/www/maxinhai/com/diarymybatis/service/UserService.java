package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface UserService {

    /**
     * 新增用户信息
     * @param user
     */
    int addUser(User user) throws Exception;

    /**
     * 删除用户信息
     * @param id
     */
    int delUser(Long id) throws Exception;

    /**
     * 修改用户信息
     * @param params
     */
    int modifyUser(Map<String, Object> params) throws Exception;

    /**
     * 根据条件查询用户数据集合
     * @param params
     * @return
     * @throws Exception
     */
    Map<String, Object> findAllByCondition(Map<String, Object> params) throws Exception;

    /**
     * 根据条件查询一条用户数据
     * @param params
     * @return
     * @throws Exception
     */
    User findOneByCondition(Map<String, Object> params) throws Exception;

}
