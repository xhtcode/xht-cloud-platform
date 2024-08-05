package com.xht.cloud.admin.module.permissions.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：菜单权限
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysMenuResponse(菜单权限-响应信息)")
public class SysMenuResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 上级菜单id
     */
    @Schema(description = "上级菜单id")
    private String parentId;

    /**
     * 菜单类型（M目录C菜单F按钮）
     */
    @Schema(description = "菜单类型（M目录C菜单F按钮）")
    private String menuType;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String menuPath;

    /**
     * 组件视图名称
     */
    @Schema(description = "组件视图名称")
    private String menuViewName;

    /**
     * 组件视图路径
     */
    @Schema(description = "组件视图路径")
    private String menuViewPath;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String menuIcon;

    /**
     * 当设置noRedirect的时候该路由在面包屑导航中不可被点击
     */
    @Schema(description = "当设置noRedirect的时候该路由在面包屑导航中不可被点击")
    private String menuRedirect;

    /**
     * 高亮侧边栏
     */
    @Schema(description = "高亮侧边栏")
    private String menuActive;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String menuAuthority;

    /**
     * 菜单状态（0隐藏1显示）
     */
    @Schema(description = "菜单状态（0隐藏1显示）")
    private String menuHidden;

    /**
     * 菜单状态（1正常）
     */
    @Schema(description = "菜单状态（1正常）")
    private String menuStatus;

    /**
     * 是否外链（1是）
     */
    @Schema(description = "是否外链（1是）")
    private String menuLink;

    /**
     * 路由缓存（1是）
     */
    @Schema(description = "路由缓存（1是）")
    private String menuCache;

    /**
     * tabs固定（1是）
     */
    @Schema(description = "tabs固定（1是）")
    private String menuAffix;

    /**
     * 菜单排序
     */
    @Schema(description = "菜单排序")
    private Integer menuSort;

}
