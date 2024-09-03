package com.xht.cloud.admin.module.user.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：用户-管理员
 *
 * @author : 小糊涂
 **/
@Data
@TableName(value = "sys_user_admin")
public class SysUserAdminDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 头像地址
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 手机号码
     */
    @TableField(value = "contact_phone")
    private String contactPhone;

}
