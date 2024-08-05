package com.xht.cloud.admin.module.user.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：用户信息扩展表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_user_profile")
public class SysUserProfileDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 身份证号码
     */
    @TableField(value = "id_card_number")
    private String idCardNumber;

    /**
     * 手机号码
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private LocalDateTime birthday;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 详细地址
     */
    @TableField(value = "address_detailed")
    private String addressDetailed;

    /**
     * 个人描述
     */
    @TableField(value = "description")
    private String description;

}
