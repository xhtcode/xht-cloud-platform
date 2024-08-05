package com.xht.cloud.admin.module.permissions.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import lombok.Data;

/**
 * 描述 ：系统角色菜单关联表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenuDO extends AbstractDO {

    /**
     *
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     *
     */
    @TableField(value = "menu_id")
    private String menuId;

}
