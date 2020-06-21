package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.User;

public interface UserService {

    /**
     * 新增用户信息
     * @param user
     */
    void addUser(User user);

    /**
     * 删除用户信息
     * @param id
     */
    void delUser(Long id);

    /**
     * 修改用户信息
     * @param user
     */
    void modifyUser(User user);

}
