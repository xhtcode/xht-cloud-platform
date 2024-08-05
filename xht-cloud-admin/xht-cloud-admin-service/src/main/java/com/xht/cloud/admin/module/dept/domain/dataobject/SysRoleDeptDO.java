package com.xht.cloud.admin.module.dept.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import lombok.Data;

/**
 * 描述 ：角色和部门关联
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_role_dept")

public class SysRoleDeptDO extends AbstractDO {

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private String deptId;

}
