package www.maxinhai.com.diarymybatis.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.maxinhai.com.diarymybatis.exception.BusinessException;
import www.maxinhai.com.diarymybatis.exception.CustomizeException;
import www.maxinhai.com.diarymybatis.util.ResultBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultBody nullPointerExceptionHandler(NullPointerException e){
        logger.error("发生空指针异常! 原因是: {}", e);
        ResultBody resultBody = new ResultBody("500", e.getMessage());
        return resultBody;
    }


    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomizeException.class)
    @ResponseBody
    public ResultBody customizeExceptionHandler(CustomizeException e){
        logger.error("发生自定义异常! 原因是: {}", e);
        return new ResultBody(e.getErrorCode(), e.getErrorMessage());
    }


    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultBody businessExceptionHandler(BusinessException e){
        logger.error("发生业务异常! 原因是: {}", e);
        return new ResultBody(e.getErrorCode(), e.getErrorMessage());
    }


    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(Exception e){
        logger.info("发生其他异常! 原因是: {}", e);
        return new ResultBody("500", e.getMessage());
    }

}
