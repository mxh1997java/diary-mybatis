package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.Diary;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DiaryMapper {

    /**
     * 获取用户全部日记
     * @return
     */
    @Select("select * from diary_diary")
    List<Diary> getAllDiary();

    /**
     *
     * 根据条件查询日记数据集合
     * @param params
     * @return
     */
    List<Diary> findAllByCondition(Map<String, Object> params);

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

    /**
     * 根据条件查询出一条日记信息
     * @param params
     * @return
     */
    Diary findOneByCondition(Map<String, Object> params);

}
