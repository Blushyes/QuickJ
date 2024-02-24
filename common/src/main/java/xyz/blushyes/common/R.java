package xyz.blushyes.common;

import java.io.Serializable;

import cn.hutool.http.HttpStatus;
import lombok.Data;

/**
 * 统一返回结果的封装类
 *
 * @param <T>
 */
@Data
public class R<T> implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Integer code;  // 响应码

    private String message;  // 响应消息

    private T data;  // 返回的数据

    private boolean success;

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.code = HttpStatus.HTTP_OK;
        r.success = true;
        r.message = "操作成功";
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = R.ok();
        r.setData(data);
        return r;
    }

    public static <T> R<T> ok(String message, T data) {
        R<T> r = R.ok(data);
        r.message = message;
        return r;
    }

    public static <T> R<T> ok(Runnable runnable) {
        runnable.run();
        return R.ok();
    }

    public static <T> R<T> ok(Runnable runnable, String message) {
        runnable.run();
        return R.ok(message, null);
    }

    public static <T> R<T> fail() {
        R<T> r = new R<>();
        r.code = HttpStatus.HTTP_INTERNAL_ERROR;
        r.success = false;
        return r;
    }

    public static <T> R<T> fail(String message) {
        R<T> r = R.fail();
        r.message = message;
        return r;
    }

    public static <T> R<T> fail(Integer code, String msg) {
        R<T> r = new R<>();
        r.message = msg;
        r.code = code;
        return r;
    }
}
