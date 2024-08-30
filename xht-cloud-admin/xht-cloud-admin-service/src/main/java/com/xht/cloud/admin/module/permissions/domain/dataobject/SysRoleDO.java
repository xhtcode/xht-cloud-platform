package com.xht.cloud.admin.module.permissions.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_role")
public class SysRoleDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 显示顺序
     */
    @TableField(value = "role_sort")
    private Integer roleSort;

    /**
     * 数据权限
     * <li class="el-select-dropdown__item"><span>1全部数据权限</span></li>
     * <li class="el-select-dropdown__item selected"><span>2自定数据权限</span></li>
     * <li class="el-select-dropdown__item hover"><span>3本部门数据权限</span></li>
     * <li class="el-select-dropdown__item"><span>4本部门及以下数据权限</span></li>
     * <li class="el-select-dropdown__item"><span>5仅本人数据权限</span></li>
     */
    @TableField(value = "data_scope")
    private DeptUserDataScopeEnum dataScope;

    /**
     * 角色状态（0停用1正常）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 角色描述
     */
    @TableField(value = "role_desc")
    private String roleDesc;

}
