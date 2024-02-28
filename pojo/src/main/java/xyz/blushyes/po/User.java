package xyz.blushyes.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.blushyes.common.TimeWithLogicPO;

/**
 * 用户表
 */
@Data
@Schema(description = "用户表")
public class User extends TimeWithLogicPO {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "是否已启用")
    private boolean enable;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}