package com.xht.cloud.admin.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.cloud.framework.core.domain.response.Response;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetaVo extends Response {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    private String activeMenu;

    /**
     * 是否外链（true）
     */
    private boolean linkStatus;

    /**
     * 是否隐藏（true）
     */
    private boolean hiddenStatus;

    /**
     * 是否全屏（true）
     */
    private boolean fullStatus;

    /**
     * 是否固定在 tabs nav（true）
     */
    private boolean affixStatus;

    /**
     * 是否缓存 （true）
     */
    private boolean keepAliveStatus;

    /**
     * 标识后端路由
     */
    private boolean backstage = true;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 排序
     */
    private int rank;

    /**
     * 角色
     */
    private List<String> roles = new ArrayList<>();
}
