package com.xht.cloud.admin.module.dept.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_position")
public class SysPositionDO extends BaseDO {

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
     * 部门id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 岗位编码
     */
    @TableField(value = "position_code")
    private String positionCode;

    /**
     * 岗位名称
     */
    @TableField(value = "position_name")
    private String positionName;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 状态（1正常0停用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

}
