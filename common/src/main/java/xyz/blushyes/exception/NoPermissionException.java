package xyz.blushyes.exception;

/**
 * 无权限异常
 */
public class NoPermissionException extends BaseException {
    public NoPermissionException() {
        super("无权限", 403);
    }
}
