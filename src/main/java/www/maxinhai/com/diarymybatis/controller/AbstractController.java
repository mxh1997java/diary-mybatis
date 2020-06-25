package www.maxinhai.com.diarymybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import www.maxinhai.com.diarymybatis.mapper.UserMapper;
import www.maxinhai.com.diarymybatis.service.Impl.DiaryServiceImpl;
import www.maxinhai.com.diarymybatis.service.Impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected UserServiceImpl userService;

    @Autowired
    protected DiaryServiceImpl diaryService;

    /**
     * 获取成功响应
     * @param message  返回信息
     * @param data     返回数据
     * @return
     */
    public Map<String, Object> getSuccess(String message, Object data) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 200);
        result.put("message", message);
        result.put("data", data);
        return result;
    }

    /**
     * 获取成功相应
     * @param data 返回数据
     * @return
     */
    public Map<String, Object> getSuccess(Object data) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", data);
        return result;
    }

    /**
     * 获取无返回数据的成功响应
     * @return
     */
    public Map<String, Object> getSuccess() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 200);
        result.put("message", "success");
        return result;
    }


    /**
     * 获取失败响应
     * @return
     */
    public Map<String, Object> getFailure() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 500);
        result.put("message", "failure");
        return result;
    }

}
