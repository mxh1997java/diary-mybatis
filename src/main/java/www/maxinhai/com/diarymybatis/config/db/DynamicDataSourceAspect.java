package www.maxinhai.com.diarymybatis.config.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private final String[] QUERY_PREFIX = {"select"};

    @Pointcut("execution( * www.maxinhai.com.diarymybatis.service.Impl.*.*(..))")
    public void daoAspect() {
    }

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Object[] params = point.getArgs();
        System.out.println(params.toString());
        String param = (String) params[0];
        for (Object string:params
        ) {
            System.out.println(string.toString());
        }
        System.out.println("###################################################");
        System.out.println(point.getSignature().getName());
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        //DynamicDataSourceContextHolder.setDataSourceKey("slave");
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.setDataSourceKey("slave");
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }
    }

    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    private Boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
