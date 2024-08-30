package com.xht.cloud.admin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.core.domain.response.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述 ：用户信息
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "用户信息", description = "包含了各种用户类型的用户数据")
public class UserCenterResponse extends AbstractResponse {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "ID")
    private String nickName;

    /**
     * 密码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "密码")
    private String passWord;

    /**
     * 密码盐值
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "密码盐值")
    private String passWordSalt;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private String deptId;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限")
    private DeptUserDataScopeEnum dataScope;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String userAvatar;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
    private UserStatusEnums userStatus;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private LocalDateTime registeredTime;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private Set<String> roleCode;

    /**
     * 菜单权限或者其他权限
     */
    @Schema(description = "权限编码")
    private Set<String> authorityCode;

    /**
     * 数据来源
     */
    @Schema(description = "数据来源")
    private String dataSource;
}
