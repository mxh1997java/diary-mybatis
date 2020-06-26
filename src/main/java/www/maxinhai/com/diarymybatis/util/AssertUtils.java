package www.maxinhai.com.diarymybatis.util;

import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.exception.BusinessException;
import www.maxinhai.com.diarymybatis.exception.CustomizeException;

/**
 * 断言
 */
@Component
public class AssertUtils {

    public static void assertTrue(boolean flag, String message) throws Exception {
        if(flag) {
            throw new RuntimeException(message);
        }
    }

    public static void assertFalse(boolean flag, String message) throws Exception {
        if(!flag) {
            throw new RuntimeException(message);
        }
    }

}
