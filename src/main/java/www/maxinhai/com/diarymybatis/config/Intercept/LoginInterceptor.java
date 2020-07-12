package www.maxinhai.com.diarymybatis.config.Intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import www.maxinhai.com.diarymybatis.config.annotation.LoginRequired;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import www.maxinhai.com.diarymybatis.util.RedisUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Value("${redis_user_key}")
    String redis_user_key;

    /**
     * 这里会出现redisTemplate为null的情况，
     * 因为拦截器加载于IOC之前，所以这个时候注入RedisTemplate时是null
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 这里会出现redisUtils为null的情况，
     * 因为拦截器加载于IOC之前，所以这个时候注入RedisTemplate时是null
     */
    @Resource
    private RedisUtils redisUtils;

    /**
     * 在请求之前调用（Controller方法调用之前）
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("调用方式:{}， 调用路径:{}, 接口参数:{}", request.getMethod(), request.getRequestURI(), request.getParameterMap().toString());

        //判断是否为ResourceHttpRequestHandler
        if(!(handler instanceof HandlerMethod)) {
            logger.warn("HandlerMethod=" + handler.getClass().getName() + ",request=" + request.getQueryString());
            return true;
        }

        //获取请求上的登陆注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired annotation = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        if(null != annotation) {
            //判断注解里的默认值
            boolean required = annotation.loginRequired();
            if(!required) {
                logger.info("调用路径: {} 不需要登陆", request.getRequestURI());
                return true;
            }
        }

        AssertUtils.assertTrue(EmptyUtils.isEmpty(request.getHeader("token")), "token不存在!");
        String token = request.getHeader("token");
        redisUtils.setRedisTemplate(redisTemplate);
        Object userInfo = redisUtils.get(redis_user_key + ":" + token);
        if(EmptyUtils.isEmpty(userInfo)) {
            logger.info("登录信息不存在!请重新登录!");
            return false;
            //throw new RuntimeException("登录信息不存在!请重新登录!");
        } else {
            //每次请求通过验证，延长token过期时间
            redisUtils.expire(redis_user_key + ":" + token, 60 * 60);
        }
        return true;
    }


    /**
     * 在请求之后调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    /**
     * 在请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
