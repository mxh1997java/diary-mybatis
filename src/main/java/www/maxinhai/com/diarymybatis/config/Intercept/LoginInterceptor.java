package www.maxinhai.com.diarymybatis.config.Intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import www.maxinhai.com.diarymybatis.util.RedisUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

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
        if("/user/registered".equals(request.getRequestURI()) || "/user/login".equals(request.getRequestURI())) {
            return true;
        } else {
            AssertUtils.assertTrue(EmptyUtils.isEmpty(request.getHeader("token")), "token不存在!");
            String token = request.getHeader("token");
            redisUtils.setRedisTemplate(redisTemplate);
            Object userInfo = redisUtils.get("USER_SESSION_KEY:" + token);
            if(EmptyUtils.isEmpty(userInfo)) {
                logger.info("登录信息不存在!请重新登录!");
                return false;
            }
        }
        return false;
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
