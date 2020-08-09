package www.maxinhai.com.diarymybatis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.mapper.DiaryMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * 功能描述: 实现InitializingBean接口，在SpringBoot启动时加载数据到redis
 * @Author: 15735400536
 * @Date: 2020/8/9 15:48
 */
@Component
public class InitBean implements InitializingBean {

    @Autowired
    private DiaryMapper diaryMapper;

    /**
     * 功能描述: Spring启动时MySQL插入一百万条数据
     * @Param: []
     * @Return: void
     * @Author: 15735400536
     * @Date: 2020/8/9 16:42
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化Bean");
        long startTime = System.currentTimeMillis();
        Date currentTime = new Date();
        for(int i=0; i<100; i++) {
            ArrayList<Diary> diaryList = new ArrayList<>(10000);
            for(int j=0; j<10000; j++) {
                Diary diary = new Diary();
                diary.setTitle("初始化数据");
                diary.setContent("初始化数据，初始者admin，初始化时间" + LocalDate.now().toString());
                diary.setAuthor("admin");
                diary.setAuthor_id("1");
                diary.setCreateTime(currentTime);
                diaryList.add(diary);
            }
            diaryMapper.batchInsertDiary(diaryList);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时: " + (endTime - startTime));
    }

}
