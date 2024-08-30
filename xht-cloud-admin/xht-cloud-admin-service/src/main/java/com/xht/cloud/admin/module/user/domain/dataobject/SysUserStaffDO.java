package com.xht.cloud.admin.module.user.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.api.user.enums.UserSexEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户-工作人员
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_user_staff")
public class SysUserStaffDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String userName;

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
     * 部门id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 部门数据权限
     */
    @TableField(value = "data_scope")
    private DeptUserDataScopeEnum dataScope;

    /**
     * 头像地址
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户状态
     */
    @TableField(value = "user_status")
    private UserStatusEnums userStatus;

    /**
     * 身份证号码
     */
    @TableField(value = "identity_card")
    private String identityCard;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @TableField(value = "user_sex")
    private UserSexEnums userSex;

    /**
     * 年龄
     */
    @TableField(value = "user_age")
    private String userAge;

    /**
     * 联系手机号
     */
    @TableField(value = "contact_mobile")
    private String contactMobile;

    /**
     * 地址id
     */
    @TableField(value = "address_id")
    private String addressId;

    /**
     * 地址名称
     */
    @TableField(value = "address_name")
    private String addressName;

    /**
     * 个性签名
     */
    @TableField(value = "user_signature")
    private String userSignature;

    /**
     * 注册时间
     */
    @TableField(value = "registered_time")
    private LocalDateTime registeredTime;



}
