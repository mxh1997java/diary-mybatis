package www.maxinhai.com.diarymybatis.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/", filterName = "requestFilter")
public class RequestFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    /**
     * 容器加载的时候调用
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("容器初始化 调用过滤器初始化方法 ...");
    }


    /**
     * 请求被拦截的时候进行调用
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("请求进入servlet容器 调用过滤器doFilter方法 ...");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        //跨域设置（也可以通过跨域注解配置）
        httpResponse.addHeader("Access-Control-Allow-Origin","*");

        //禁止浏览器缓存所有动态页面
        httpResponse.setDateHeader("Expires", -1);
        httpResponse.setHeader("Cache-Control", "no-cache");
        httpResponse.setHeader("Pragma", "no-cache");

        //映射请求地址
        String path = httpRequest.getRequestURI();
        if(path.indexOf("/api/") < 0) {
            path = "/api" + path;
            httpRequest.getRequestDispatcher(path).forward(servletRequest, httpResponse);
        } else {
            filterChain.doFilter(servletRequest, httpResponse);
        }
    }


    /**
     * 容器被销毁的时候被调用
     */
    @Override
    public void destroy() {
        logger.info("容器销毁 调用过滤器销毁方法 ...");
    }
}
