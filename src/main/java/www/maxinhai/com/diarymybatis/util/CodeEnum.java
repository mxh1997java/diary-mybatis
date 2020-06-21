package www.maxinhai.com.diarymybatis.util;

/**
 * 响应状态码和说明
 */
public class CodeEnum {

    SUCCESS(0, "成功!"),
    FAIL(1, "失败，未知错误!")
​
    /**
     * 响应状态码
     */
    private final int code;
​
    /**
     * 响应提示
     */
    private final String msg;
​
    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
​
    public int getCode() {
        return code;
    }
​
    public String getMsg() {
        return msg;
    }

}
