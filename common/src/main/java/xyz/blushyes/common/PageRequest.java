package xyz.blushyes.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页查询基类
 */
@Data
public class PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "页码")
    private Integer pageNo = 1;

    @Schema(name = "页大小")
    private Integer pageSize = 10;

    @Schema(name = "是否计数")
    private Boolean count = true;
}