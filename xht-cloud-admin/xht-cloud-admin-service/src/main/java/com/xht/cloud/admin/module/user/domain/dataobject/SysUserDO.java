package com.xht.cloud.admin.module.user.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_user")
public class SysUserDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 密码
     */
    @TableField(value = "pass_word")
    private String passWord;

    /**
     * 密码盐值
     */
    @TableField(value = "pass_word_salt")
    private String passWordSalt;

    /**
     * 旧密码
     */
    @TableField(value = "pass_word_old")
    private String passWordOld;

    /**
     * 旧密码盐值
     */
    @TableField(value = "pass_word_salt_old")
    private String passWordSaltOld;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 头像地址
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 账号类型
     */
    @TableField(value = "user_type")
    private UserTypeEnums userType;

    /**
     * QQ互联openid
     */
    @TableField(value = "qq_openid")
    private String qqOpenid;

    /**
     * 微信openid
     */
    @TableField(value = "wx_openid")
    private String wxOpenid;

    /**
     * 微信unionid
     */
    @TableField(value = "wx_unionid")
    private String wxUnionid;

    /**
     * 用户状态
     */
    @TableField(value = "user_status")
    private UserStatusEnums userStatus;

    /**
     * 是否超级管理员，0-不是，1-是
     */
    @TableField(value = "admin_status")
    private SuperAdminUserEnums adminStatus;

    /**
     * 注册时间
     */
    @TableField(value = "registered_time")
    private LocalDateTime registeredTime;

    /**
     * 最后一次登录时间
     */
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;


}
