package com.xht.cloud.admin.module.org.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.module.org.constant.SysOrgTypeEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Data
@TableName("sys_org")
public class SysOrgDO extends BaseDO implements Serializable {

    /**
     * 机构id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 主管ID(用户id)
     */
    @TableField(value = "director_id")
    private String directorId;

    /**
     * 上级机构id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 上级机构名称
     */
    @TableField(value = "parent_name")
    private String parentName;

    /**
     * 机构名称
     */
    @TableField(value = "org_name")
    private String orgName;

    /**
     * 机构编码
     */
    @TableField(value = "org_code")
    private String orgCode;

    /**
     * 机构分类
     */
    @TableField(value = "org_type")
    private SysOrgTypeEnums orgType;

    /**
     * 机构状态
     */
    @TableField(value = "org_status")
    private String orgStatus;

    /**
     * 机构排序
     */
    @TableField(value = "org_sort")
    private Integer orgSort;

    /**
     * 机构名称
     */
    @TableField(value = "org_path")
    private String orgPath;

    /**
     * 机构描述
     */
    @TableField(value = "org_desc")
    private String orgDesc;

    /**
     * 联系电话
     */
    @TableField(value = "org_phone")
    private String orgPhone;

    /**
     * 联系邮箱
     */
    @TableField(value = "org_email")
    private String orgEmail;

}
