package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.Diary;
import java.util.List;
import java.util.Map;

public interface DiaryService {

    List<Diary> findAllDiary(Map<String, Object> params);

    Diary findOneById(Long id);

    int addDiary(Diary diary);

    int modifyDiary(Diary diary);

    int deleteDiary(Long id);

    int batchInsertDiary(List<Diary> diaryList);

}
