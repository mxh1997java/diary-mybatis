package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.RegisteredInfo;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RegisteredInfoMapper {

    /**
     * 功能描述: 添加用户注册记录
     * @Param: [info]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/26 19:04
     */
    int addRegisteredInfo(RegisteredInfo info);

    /**
     * 功能描述: 根据条件查询注册记录
     * @Param: [params]
     * @Return: java.util.List<www.maxinhai.com.diarymybatis.entity.RegisteredInfo>
     * @Author: 15735400536
     * @Date: 2020/7/26 19:04
     */
    List<RegisteredInfo> findAllByCondition(Map<String, Object> params);

}
