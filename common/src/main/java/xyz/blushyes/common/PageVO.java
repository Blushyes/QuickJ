package xyz.blushyes.common;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Schema(description = "分页结果基类")
public class PageVO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码")
    private Integer pageNo;

    @Schema(description = "页大小")
    private Integer pageSize;

    @Schema(description = "总数")
    private Integer total;

    @Schema(description = "分页明细")
    private List<T> rows;

    public static <T> PageVO<T> emptyPage() {
        PageVO<T> empty = new PageVO<>();
        empty.setPageNo(0);
        empty.setPageSize(0);
        empty.setTotal(0);
        empty.setRows(Collections.emptyList());
        return empty;
    }

    public Boolean hasNext() {
        return !CollectionUtils.isEmpty(rows) && total > pageSize && (total > (pageNo - 1) * pageSize + rows.size());
    }
}
