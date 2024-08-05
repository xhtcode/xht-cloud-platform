package com.xht.cloud.admin.api.user.dto;

import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import lombok.Data;

import java.util.Set;

/**
 * 描述 ： 用户信息
 *
 * @author 小糊涂
 **/
@Data
public class SysUserResDTO {

    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 密码.
     */
    private String passWord;

    /**
     * 超级管理员标识 0否 1是.
     */
    private SuperAdminUserEnums superAdmin;

    /**
     * 数据权限级别
     */
    private Integer dataScope;

    /**
     * 用户状态 0启用 1禁用.
     */
    private UserStatusEnums userStatus;

    /**
     * 手机号.
     */
    private String mobile;

    /**
     * 部门ID.
     */
    private String deptId;

    /**
     * 菜单权限标识集合.
     */
    private Set<String> menuCode;

    /**
     * 角色Code
     */
    private Set<String> roleCode;

    /**
     * 数据源名称.
     */
    private String sourceName;
}
