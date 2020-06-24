package www.maxinhai.com.diarymybatis.util;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolUtils {

    /**
     * 默认10根核心线程
     * 线程数量不够用，最大增长到1000根，
     * 任务队列默认可以存放1000个任务，
     * 每根线程的活跃时间是1秒
     */
    private static final ExecutorService THREADPOOL = new ThreadPoolExecutor(10,
            100000,
            1,
            TimeUnit.MICROSECONDS,
            new ArrayBlockingQueue<Runnable>(100000){

    });


    /**
     * 获取线程池
     * @return
     */
    public static ExecutorService getThreadPool() {
        return THREADPOOL;
    }

}
