package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import com.xht.cloud.framework.web.validation.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户信息扩展表-公共请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserProfileRequest(用户信息扩展表-公共请求信息)")
public class SysUserProfileRequest implements IUpdateRequestFun<String> {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID `userId` 校验不通过", groups = {Update.class})
    private String userId;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名 `realName` 校验不通过")
    private String realName;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @Schema(description = "性别，0：未知，1：男，2：女")
    @NotBlank(message = "性别，0：未知，1：男，2：女 `gender` 校验不通过")
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

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.userId;
    }
}
