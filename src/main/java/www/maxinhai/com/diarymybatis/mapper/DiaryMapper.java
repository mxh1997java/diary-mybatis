package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Insert;
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

    /**
     * 功能描述: 插入一百万条数据，用批量插入方式
     *           千万不要搞个一百万容量的List，然后for循环填充对象，这样会内存溢出
     *           先搞个10000容量的List，for循环填充数据，然后再批量插入，反复10次，大概耗时3分钟以内
     * @Param: [diaryList]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/8/9 16:30
     */
    int batchInsertDiary(List<Diary> diaryList);

    /**
     * 根据条件查询出一条日记信息
     * @param params
     * @return
     */
    Diary findOneByCondition(Map<String, Object> params);

    /**
     * 功能描述: 如果要插入一百万条数据，千万不要一条一条插
     * @Param: []
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/8/9 16:30
     */
    @Insert("INSERT INTO `diary`.`diary_diary` (`title`, `content`, `author_id`, `author`, `createTime`) " +
            "VALUES ('初始化数据', '初始化数据，初始者admin，初始化时间20200809', '1', 'admin', '2020-08-09 00:00:00')")
    int initDiaryInfo();

}
