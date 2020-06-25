package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.LoginInfo;

import java.util.List;
import java.util.Map;

public interface LoginInfoServce {


    int addLoginInfo(LoginInfo info);


    List<LoginInfo> findAllByCondition(Map<String, Object> params);


}
