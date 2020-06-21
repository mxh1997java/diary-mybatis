package www.maxinhai.com.diarymybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import www.maxinhai.com.diarymybatis.mapper.DiaryMapper;
import www.maxinhai.com.diarymybatis.mapper.UserMapper;

public abstract class AbstractService {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected DiaryMapper diaryMapper;

}
