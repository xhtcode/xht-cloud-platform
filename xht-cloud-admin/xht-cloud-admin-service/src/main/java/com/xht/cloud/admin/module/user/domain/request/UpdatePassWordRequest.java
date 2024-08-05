package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.UpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Schema(description = "管理后台 - 用户个人中心更新密码 Request VO")
@Data
public class UpdatePassWordRequest extends UpdateRequest<String> {

    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "旧密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "654321")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String newPassword;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return null;
    }
}
