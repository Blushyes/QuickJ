package xyz.blushyes.common;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "页码")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "是否计数")
    private Boolean count = true;
}