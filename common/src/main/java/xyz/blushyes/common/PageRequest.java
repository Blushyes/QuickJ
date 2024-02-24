package xyz.blushyes.common;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页查询基类
 */
@Data
@Schema(description = "分页请求基类")
public class PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码")
    private Integer pageNo = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;

    @Schema(description = "是否计数")
    private Boolean count = true;
}