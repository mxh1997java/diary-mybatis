package www.maxinhai.com.diarymybatis.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求幂等性验证(测试时在接口上加该注解)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface Idempotent {

    /**
     * 是否需要校验，默认需要校验
     * @return
     */
    boolean loginRequired() default true;

}
