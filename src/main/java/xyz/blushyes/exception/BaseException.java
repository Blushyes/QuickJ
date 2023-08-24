package xyz.blushyes.exception;

/**
 * 自定义业务异常类
 */
public class BaseException extends RuntimeException {
    public BaseException(String msg) {
        super(msg);
    }
}
