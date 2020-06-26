package www.maxinhai.com.diarymybatis.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略token验证(测试时在接口上加该注解)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface LoginRequired {

    /**
     * 是否需要登陆，缺省为不需要
     * @return
     */
    boolean loginRequired() default false;

}
