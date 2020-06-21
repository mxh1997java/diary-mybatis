package www.maxinhai.com.diarymybatis.service.Impl;

import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.DiaryService;
import java.util.List;
import java.util.Map;

@Service
public class DiaryServiceImpl extends AbstractService implements DiaryService {

    @Override
    public List<Diary> findAllDiary(Map<String, Object> params) {
        diaryMapper.getAllDiary();
        return null;
    }

    @Override
    public Diary findOneById(Long id) {
        return null;
    }

    @Override
    public int addDiary(Diary diary) {
        return 0;
    }

    @Override
    public int modifyDiary(Diary diary) {
        return 0;
    }

    @Override
    public int deleteDiary(Long id) {
        return 0;
    }

    @Override
    public int batchInsertDiary(List<Diary> diaryList) {
        return 0;
    }
}
