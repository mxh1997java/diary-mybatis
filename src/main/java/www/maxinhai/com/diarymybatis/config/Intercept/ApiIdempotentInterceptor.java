package www.maxinhai.com.diarymybatis.config.Intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import www.maxinhai.com.diarymybatis.config.annotation.Idempotent;
import www.maxinhai.com.diarymybatis.service.Impl.TokenServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 功能描述: 请求幂等性校验
 * @Author: 15735400536
 * @Date: 2020/7/16 10:51
 */
@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idempotent methodAnnotation = method.getAnnotation(Idempotent.class);
        if (methodAnnotation != null) {
            String key = request.getHeader("token").concat(request.getRequestURI());
            boolean checkToken = tokenService.checkToken(key);
            if(checkToken) {
                throw new RuntimeException("请求重复!");
            } else {
                tokenService.addToken(key, request.getHeader("token"));
            }
            // 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
