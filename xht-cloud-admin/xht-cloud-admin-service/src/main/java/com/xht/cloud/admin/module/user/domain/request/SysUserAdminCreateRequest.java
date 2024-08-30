package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.TelephoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysUserAdminCreateRequest(管理员-增加请求信息)")
public class SysUserAdminCreateRequest extends CreateRequest {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @NotEmpty(message = "用户账号不合法")
    private String userName;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String passWord;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @NotEmpty(message = "用户账号不合法")
    private String userAvatar;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @TelephoneNumber
    private String contactMobile;

}