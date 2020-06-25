package www.maxinhai.com.diarymybatis.util;

/**
 * 响应状态码和说明
 */
public enum CodeEnum {

    //当enum为class时:
    //public static final CodeEnum SUCCESS = new CodeEnum(0, "成功!");
    //public static final CodeEnum FAIL = new CodeEnum(1, "失败，未知错误!");

    SUCCESS(200, "成功!"),
    NOT_FOUND(404, "未找到该资源!"),
    FAIL(500, "服务器内部错误!");

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     */
    private final String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
