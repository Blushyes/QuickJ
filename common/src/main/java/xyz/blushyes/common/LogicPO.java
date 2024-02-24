package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LogicPO extends SimplePO {
    @TableLogic
    @Schema(description = "逻辑删除标记")
    private Boolean deleteFlag;
}
