package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface LoginInfoMapper {


    /**
     * 添加登录信息
     * @param loginInfo
     * @return
     */
    int addLoginInfo(LoginInfo loginInfo);


    /**
     * 根据条件查询
     * @param params
     * @return
     */
    List<LoginInfo> findAllByCondition(Map<String, Object> params);

}
