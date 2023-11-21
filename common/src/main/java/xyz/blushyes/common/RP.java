package xyz.blushyes.common;

import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应结果基类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RP<T> extends PageVO<T> implements Serializable {
    @Schema(name = "响应码")
    private Integer code;

    @Schema(name = "响应消息")
    private String msg;

    private RP() {
    }

    public static <T> RP<T> ok() {
        return ok(null);
    }

    public static <T> RP<T> ok(PageVO<T> vo) {
        return null == vo ?
                restResult(HttpStatus.HTTP_OK, null, 0, 1, 10, Collections.emptyList()) :
                restResult(HttpStatus.HTTP_OK, null, vo.getTotal(), vo.getPageNo(), vo.getPageSize(), vo.getRows());
    }

    public static <T> RP<T> fail() {
        return fail(null);
    }

    public static <T> RP<T> fail(String msg) {
        return fail(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static <T> RP<T> fail(int code, String msg) {
        return restResult(code, msg, 0, 0, 0, Collections.emptyList());
    }

    public static <T> RP<T> restResult(int code, String msg, Integer total, Integer pageNo, Integer pageSize, List<T> rows) {
        RP<T> result = new RP<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setTotal(total);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setRows(rows);
        return result;
    }
}
