package www.maxinhai.com.diarymybatis.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import www.maxinhai.com.diarymybatis.config.db.DynamicDataSource;
import www.maxinhai.com.diarymybatis.config.db.DynamicDataSourceContextHolder;
import www.maxinhai.com.diarymybatis.config.db.SwitchDB;
import www.maxinhai.com.diarymybatis.entity.User;
import java.util.Map;

@Api(tags = "数据库管理接口文档")
@RequestMapping(value = "api/db/")
@RestController
public class DBController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 手动切换数据库
     */
    @Autowired
    private SwitchDB switchDB;

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @GetMapping(value = "/switchDB")
    public Map<String, Object> testProcess(@RequestParam("dbName") String dbName, @RequestParam("dbId") Integer dbId){
        switchDB.change(dbName,dbId);
        //获取当前已有的数据源实例
        System.out.println(dynamicDataSource.getDataSourceMap());
        return process();
    }


    /**
     * 事务测试
     * 注意：(1)有@Transactional注解的方法，方法内部不可以做切换数据库操作
     *      (2)在同一个service其他方法调用带@Transactional的方法，事务不起作用，（比如：在本类中使用testProcess调用process()）
     *         可以用其他service中调用带@Transactional注解的方法，或在controller中调用.
     * @return
     */
    //propagation 传播行为 isolation 隔离级别  rollbackFor 回滚规则
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public Map<String, Object> process() {
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        logger.info("＝＝＝＝＝service当前连接的数据库是:" + currentKey);
        User user = new User();
        user.setUser_id(1L);
        user.setUsername("maxinhai");
        user.setPassword("maxinhai");
        int insertResult = userMapper.addUser(user);
        if(insertResult > 0) {
            return getSuccess();
        }
        return getFailure();
    }

}
