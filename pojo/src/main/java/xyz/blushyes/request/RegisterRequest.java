package xyz.blushyes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "注册请求")
public class RegisterRequest {
    @Schema(description = "用户名")
    @Size(min = 6, max = 20, message = "用户名长度必须在6-20之间")
    private String username;

    @Schema(description = "密码")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;
}
