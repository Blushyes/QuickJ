package xyz.blushyes.exception;

/**
 * 自定义业务异常类
 */
public class BaseException extends RuntimeException {
    private final int code;

    public int getCode() {
        return code;
    }

    public BaseException(String msg) {
        super(msg);
        this.code = 500;
    }
    public BaseException(String msg, int code) {
        super(msg);
        this.code = code;
    }
}
