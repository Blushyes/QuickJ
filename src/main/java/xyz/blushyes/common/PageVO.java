package xyz.blushyes.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果基类
 */
@Data
@ApiModel("分页结果基类")
public class PageVO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "分页明细")
    private List<T> rows;

    public Boolean hasNext() {
        return !CollectionUtils.isEmpty(rows) && total > pageSize && (total > (pageNo - 1) * pageSize + rows.size());
    }

    public static <T> PageVO<T> emptyPage() {
        PageVO<T> empty = new PageVO<>();
        empty.setPageNo(0);
        empty.setPageSize(0);
        empty.setTotal(0);
        empty.setRows(Collections.emptyList());
        return empty;
    }
}
