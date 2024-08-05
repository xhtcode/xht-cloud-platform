package com.xht.cloud.admin.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.cloud.framework.core.domain.response.Response;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述 ：用户表
 *
 * @author 小糊涂
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "SysUserResponse(用户表-响应信息)")
public class SysUserResponse extends Response {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String id;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userAccount;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 密码盐值
     */
    private String passWordSalt;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private String deptId;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String userAvatar;

    /**
     * 账号类型
     */
    @Schema(description = "账号类型")
    private UserTypeEnums userType;

    /**
     * QQ互联openid
     */
    @Schema(description = "QQ互联openid")
    private String qqOpenid;

    /**
     * 微信openid
     */
    @Schema(description = "微信openid")
    private String wxOpenid;

    /**
     * 微信unionid
     */
    @Schema(description = "微信unionid")
    private String wxUnionid;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
     private UserStatusEnums userStatus;

    /**
     * 是否超级管理员，0-不是，1-是
     */
    @Schema(description = "是否超级管理员，0-不是，1-是")
     private SuperAdminUserEnums adminStatus;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private LocalDateTime registeredTime;

    /**
     * 最后一次登录时间
     */
    @Schema(description = "最后一次登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "角色code")
    private Set<String> roleCode;

    @Schema(description = "数据权限")
    private Integer dataScope;

}
