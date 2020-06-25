package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.DiaryService;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiaryServiceImpl extends AbstractService implements DiaryService {

    @Override
    public List<Diary> findAllDiary(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "缺少必要参数!");
        return diaryMapper.findAllByCondition(params);
    }

    @Override
    public Diary findOneById(Long id) throws Exception {
        return getDiaryById(id);
    }

    @Override
    public int addDiary(Diary diary) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diary.getTitle()), "标题不为空!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diary.getAuthor_id()), "作者id不为空!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diary.getAuthor()), "作者不为空!");
        Map<String, Object> params = new HashMap<>();
        params.put("title", diary.getTitle());
        params.put("author", diary.getAuthor());
        params.put("author_id", diary.getAuthor_id());
        Diary findResult = diaryMapper.findOneByCondition(params);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(findResult), "标题已存在!");
        diary.setCreateTime(new Date());
        return diaryMapper.addDiary(diary);
    }

    @Override
    public int modifyDiary(Diary diary) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diary), "缺少必要参数!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diary.getId()), "缺少必要参数!");
        Diary findResult = getDiaryById(diary.getId());
        AssertUtils.assertTrue(EmptyUtils.isEmpty(findResult), "数据不存在!");
        diary.setModifyTime(new Date());
        return diaryMapper.updateDiaryById(diary);
    }

    @Override
    public int deleteDiary(Long id) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(id), "缺少必要参数!");
        return diaryMapper.deleteDiaryById(id);
    }

    @Override
    public int batchInsertDiary(List<Diary> diaryList) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(diaryList), "缺少必要参数!");
        return diaryMapper.batchInsertDiary(diaryList);
    }

}
