package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.Diary;
import java.util.List;

@Repository
@Mapper
public interface DiaryMapper {

    /**
     * 获取用户全部日记
     * @return
     */
    //@Select("select * from diary_diary")
    List<Diary> getAllDiary();

    /**
     * 根据id查询出一条日记信息
     * @param id
     * @return
     */
    Diary findOneDiaryById(Long id);

    int addDiary(Diary diary);

    int deleteDiaryById(Long id);

    int updateDiaryById(Diary diary);

    int batchInsertDiary(List<Diary> diaryList);

}
