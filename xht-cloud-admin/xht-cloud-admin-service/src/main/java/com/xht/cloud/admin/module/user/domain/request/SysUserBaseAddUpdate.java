package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserBaseAddUpdate(用户表-增改)")
public class SysUserBaseAddUpdate extends CreateRequest {

    @Valid
    @NotNull(message = "用户信息不通过")
    private SysUserUpdateRequest sysUser;

    @Valid
    @NotNull(message = "用户基础校验不通过")
    private SysUserProfileRequest profile;

}
