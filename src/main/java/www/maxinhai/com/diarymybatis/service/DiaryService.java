package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.Diary;
import java.util.List;
import java.util.Map;

public interface DiaryService {

    List<Diary> findAllDiary(Map<String, Object> params) throws Exception;

    Diary findOneById(Long id) throws Exception;

    int addDiary(Diary diary) throws Exception;

    int modifyDiary(Diary diary) throws Exception;

    int deleteDiary(Long id) throws Exception;

    int batchInsertDiary(List<Diary> diaryList) throws Exception;

}
