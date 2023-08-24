package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePO extends TimePO {
    @ApiModelProperty(value = "创建者openid")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "更新者openid")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
}
