package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.User;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 字段映射注解
     * @return
     */
    @Results({
            @Result(column = "user_id",property = "user_id"),
            @Result(column = "username",property = "name"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password")
    })
    @Select("select * from diary_user")
    List<User> getAllUser();

    int addUser(User user);

    /**
     * 根据id或者username查询一个用户信息
     * @param user
     * @return
     */
    User selectOneByCondition(User user);


    /**
     * 根据条件查询用户信息
     * @param params
     * @return
     */
    List<User> findAllByCondition(Map<String, Object> params);

    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    int deleteUserById(Long id);

    /**
     * 根据id修改用户数据
     * @param user
     * @return
     */
    int updateUserById(User user);
}
