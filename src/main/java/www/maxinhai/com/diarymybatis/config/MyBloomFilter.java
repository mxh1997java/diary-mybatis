package www.maxinhai.com.diarymybatis.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.mapper.DiaryMapper;
import www.maxinhai.com.diarymybatis.mapper.UserMapper;
import www.maxinhai.com.diarymybatis.util.ThreadPoolUtils;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 功能描述: 布隆过滤器，防止缓存穿透、缓存雪崩、数据去重
 * 〈〉
 * @Param:
 * @Return:
 * @Author: 15735400536
 * @Date: 2020/7/9 23:07
 */
@Component
public class MyBloomFilter {

    private final static Logger logger = LoggerFactory.getLogger(MyBloomFilter.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiaryMapper diaryMapper;

    private static int total = 1000000;
    private static BloomFilter<Integer> userBooleanFilter = BloomFilter.create(Funnels.integerFunnel(), total);
    private static BloomFilter<Integer> diaryBooleanFilter = BloomFilter.create(Funnels.integerFunnel(), total);

    /**
     * 功能描述: 将一些可能会被大量请求的数据放入布隆过滤器
     * 〈〉
     * @Param: []
     * @Return: void
     * @Author: 15735400536
     * @Date: 2020/7/9 22:53
     */
    @PostConstruct
    public void init() throws Exception{
        logger.info("加载布隆过滤器配置...");
        //如果觉得两个查询数据速度过慢的话，可以异步查询
        ExecutorService threadPool = ThreadPoolUtils.getThreadPool();
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                List<User> allUserData = userMapper.findAllByCondition(null);
                final Iterator<User> userIterator = allUserData.iterator();
                while (userIterator.hasNext()) {
                    User user = userIterator.next();
                    userBooleanFilter.put(user.getUsername().hashCode());
                }
            }
        });
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                List<Diary> allDiaryData = diaryMapper.getAllDiary();
                Iterator<Diary> diaryIterator = allDiaryData.iterator();
                while (diaryIterator.hasNext()) {
                    Diary diary = diaryIterator.next();
                    diaryBooleanFilter.put(diary.getTitle().hashCode());
                }
            }
        });
    }


    /**
     * 功能描述: 校验key是否存在
     * 〈〉
     * @Param: [key]
     * @Return: boolean
     * @Author: 15735400536
     * @Date: 2020/7/9 23:12
     */
    public boolean isExistOfDiary(int key) {
        return diaryBooleanFilter.mightContain(key);
    }

    /**
     * 功能描述: 校验key是否存在
     * 〈〉
     * @Param: [key]
     * @Return: boolean
     * @Author: 15735400536
     * @Date: 2020/7/9 23:12
     */
    public boolean isExistOfUser(int key) {
        return userBooleanFilter.mightContain(key);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("系统运行结束");
    }

}
