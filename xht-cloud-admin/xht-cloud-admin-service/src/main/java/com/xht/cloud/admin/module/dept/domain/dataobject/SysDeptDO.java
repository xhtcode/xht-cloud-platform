package com.xht.cloud.admin.module.dept.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.enums.DeptStatusEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_dept")
public class SysDeptDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 父id
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 指定主管ID(用户id)
     */
    @TableField(value = "director_id")
    private String directorId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 部门编码
     */
    @TableField(value = "dept_code")
    private String deptCode;

    /**
     * 部门负责人
     */
    @TableField(value = "dept_leader")
    private String deptLeader;

    /**
     * 联系电话
     */
    @TableField(value = "dept_tel")
    private String deptTel;

    /**
     * 排序
     */
    @TableField(value = "dept_sort")
    private Integer deptSort;

    /**
     * 部门状态
     */
    @TableField(value = "dept_status")
    private DeptStatusEnums deptStatus;

    /**
     * 部门描述
     */
    @TableField(value = "description")
    private String description;

}
