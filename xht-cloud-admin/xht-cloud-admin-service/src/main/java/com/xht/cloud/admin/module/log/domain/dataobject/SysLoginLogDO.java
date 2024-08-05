package com.xht.cloud.admin.module.log.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.log.enums.LoginStatusEnums;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：系统登录日志记录
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_login_log")
public class SysLoginLogDO extends AbstractDO {

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private String userType;

    /**
     * 登录类型
     */
    @TableField(value = "login_type")
    private String loginType;

    /**
     * 登录时间
     */
    @TableField(value = "login_time")
    private LocalDateTime loginTime;

    /**
     * 登录的IP地址
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 登录的归属地
     */
    @TableField(value = "login_address")
    private String loginAddress;

    /**
     * 登录的浏览器
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 登录状态 0登录成功 1登录失败
     */
    @TableField(value = "login_status")
    private LoginStatusEnums loginStatus;

    /**
     * 登录描述
     */
    @TableField(value = "login_desc")
    private String loginDesc;

}
