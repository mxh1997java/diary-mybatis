package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

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
}
