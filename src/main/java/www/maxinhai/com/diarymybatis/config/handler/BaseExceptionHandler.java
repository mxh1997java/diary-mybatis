package www.maxinhai.com.diarymybatis.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.maxinhai.com.diarymybatis.exception.MyException;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public MyException error(Exception e){
        if(e instanceof MyException) {
            MyException exception = (MyException) e;
        }
        e.printStackTrace();
        return new MyException(e.getMessage());
    }

}
