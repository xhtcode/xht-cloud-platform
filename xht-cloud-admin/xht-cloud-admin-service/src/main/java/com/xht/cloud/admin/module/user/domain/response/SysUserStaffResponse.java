package com.xht.cloud.admin.module.user.domain.response;

import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.api.user.enums.UserSexEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.framework.domain.response.Response;
import com.xht.cloud.framework.jackson.desensitization.annotation.SensitiveField;
import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户表
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "工作人员信息")
public class SysUserStaffResponse extends Response {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String id;


    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String nickName;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private String deptId;

    /**
     * 部门数据权限
     */
    @Schema(description = "部门数据权限")
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
     * 身份证号码
     */
    @Schema(description = "身份证号码")
    @SensitiveField(type = SensitiveFieldConstant.ID_CARD)
    private String identityCard;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @Schema(description = "性别")
    private UserSexEnums userSex;

    /**
     * 年龄
     */
    @Schema(description = "年龄")
    private String userAge;

    /**
     * 联系手机号
     */
    @Schema(description = "联系手机号")
    @SensitiveField(type = SensitiveFieldConstant.PHONE)
    private String contactMobile;

    /**
     * 地址id
     */
    @Schema(description = "地址id")
    private String addressId;

    /**
     * 地址名称
     */
    @Schema(description = "地址名称")
    @SensitiveField(type = SensitiveFieldConstant.ADDRESS)
    private String addressName;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    private String userSignature;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private LocalDateTime registeredTime;

}
