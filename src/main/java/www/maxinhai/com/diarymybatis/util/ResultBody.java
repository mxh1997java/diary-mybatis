package www.maxinhai.com.diarymybatis.util;

public class ResultBody {

    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {
    }

    public ResultBody(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBody(String code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

}
