package www.maxinhai.com.diarymybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.mapper.DiaryMapper;
import www.maxinhai.com.diarymybatis.mapper.UserMapper;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;

public abstract class AbstractService {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected DiaryMapper diaryMapper;

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    protected User getUserById(Long id) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(id), "缺少必要参数!");
        User user = new User();
        user.setUser_id(id);
        User selectResult = userMapper.selectOneByCondition(user);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(selectResult), "数据不存在!");
        return selectResult;
    }


    /**
     * 根据id查询日记数据
     * @param id
     * @return
     * @throws Exception
     */
    protected Diary getDiaryById(Long id) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(id), "缺少必要参数!");
        Diary selectResult = diaryMapper.findOneDiaryById(id);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(selectResult), "数据不存在!");
        return selectResult;
    }

}
