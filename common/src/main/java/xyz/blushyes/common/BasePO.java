package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePO extends TimePO {
    @Schema(description = "创建者openid")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @Schema(description = "更新者openid")
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;
}
