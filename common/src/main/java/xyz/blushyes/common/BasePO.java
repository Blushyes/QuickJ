package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePO extends TimePO {
    @Schema(name = "创建者openid")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(name = "更新者openid")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
}
