package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePO extends TimeWithLogicPO {
    @Schema(description = "创建者openid")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "更新者openid")
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedBy;
}
