package xyz.blushyes.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogicPO implements Serializable {
    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(name = "主键")
    private Long id;

    @TableLogic
    @Schema(name = "逻辑删除标记")
    private Boolean deleteFlag;
}
