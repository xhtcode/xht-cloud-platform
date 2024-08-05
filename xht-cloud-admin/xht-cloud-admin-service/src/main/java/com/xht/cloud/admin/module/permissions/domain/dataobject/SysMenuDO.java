package com.xht.cloud.admin.module.permissions.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：菜单权限表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_menu")
public class SysMenuDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 菜单类型（M目录C菜单F按钮）
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 路由地址
     */
    @TableField(value = "menu_path")
    private String menuPath;

    /**
     * 组件视图名称
     */
    @TableField(value = "menu_view_name")
    private String menuViewName;

    /**
     * 组件视图路径
     */
    @TableField(value = "menu_view_path")
    private String menuViewPath;

    /**
     * 菜单图标
     */
    @TableField(value = "menu_icon")
    private String menuIcon;

    /**
     * 当设置noRedirect的时候该路由在面包屑导航中不可被点击
     */
    @TableField(value = "menu_redirect")
    private String menuRedirect;

    /**
     * 高亮侧边栏
     */
    @TableField(value = "menu_active")
    private String menuActive;

    /**
     * 权限标识
     */
    @TableField(value = "menu_authority")
    private String menuAuthority;

    /**
     * 菜单状态（0隐藏1显示）
     */
    @TableField(value = "menu_hidden")
    private String menuHidden;

    /**
     * 菜单状态（1正常）
     */
    @TableField(value = "menu_status")
    private String menuStatus;

    /**
     * 是否外链（1是）
     */
    @TableField(value = "menu_link")
    private String menuLink;

    /**
     * 路由缓存（1是）
     */
    @TableField(value = "menu_cache")
    private String menuCache;

    /**
     * tabs固定（1是）
     */
    @TableField(value = "menu_affix")
    private String menuAffix;

    /**
     * 菜单排序
     */
    @TableField(value = "menu_sort")
    private Integer menuSort;


}
