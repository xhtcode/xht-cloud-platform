package com.xht.cloud.admin.module.user.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户信息扩展
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserProfileResponse(用户信息扩展-响应信息)")
public class SysUserProfileResponse extends Response {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @Schema(description = "性别，0：未知，1：男，2：女")
    private String gender;

    /**
     * 身份证号码
     */
    @Schema(description = "身份证号码")
    private String idCardNumber;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDateTime birthday;

    /**
     * 地址
     */
    @Schema(description = "地址")
    private String address;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String addressDetailed;

    /**
     * 个人描述
     */
    @Schema(description = "个人描述")
    private String description;

}
