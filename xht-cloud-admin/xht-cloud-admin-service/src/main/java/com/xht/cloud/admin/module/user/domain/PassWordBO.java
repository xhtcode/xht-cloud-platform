package com.xht.cloud.admin.module.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "管理后台 - 用户个人中心更新密码")
public class PassWordBO {

    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "旧密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度为 6-16 位")
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "654321")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度为 6-16 位")
    private String newPassword1;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "654321")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度为 6-16 位")
    private String newPassword2;
}
