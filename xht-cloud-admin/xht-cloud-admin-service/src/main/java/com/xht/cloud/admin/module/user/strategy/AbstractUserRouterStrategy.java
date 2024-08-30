package com.xht.cloud.admin.module.user.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.user.domain.response.MetaVo;
import com.xht.cloud.admin.module.user.domain.response.RouterVo;
import com.xht.cloud.admin.module.user.factory.UserRouterFactory;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import com.xht.cloud.framework.utils.treenode.TreeUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.xht.cloud.admin.constant.MenuConstant.*;

/**
 * 描述 ：策略模式 用户路由获取
 *
 * @author : 小糊涂
 **/
public abstract class AbstractUserRouterStrategy implements IUserStrategy {

    /**
     * 根据用户账号获取路由信息
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    public final List<INode<String>> getRouter(String userName) {
        Assert.hasText(userName, "nickName is empty!");
        return convert(getMenuList(userName));
    }


    /**
     * 根据用户账号获取菜单信息
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    protected abstract List<SysMenuDO> getMenuList(String userName);

    /**
     * 菜单数据转换成路由结构
     */
    private List<INode<String>> convert(List<SysMenuDO> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<INode<String>> result = new ArrayList<>(menus.size());
        for (SysMenuDO menu : menus) {
            RouterVo routerVo = new RouterVo();
            routerVo.setName(StringUtils.emptyToDefault(menu.getMenuViewName(), menu.getId()));
            routerVo.setPath(menu.getMenuPath());
            routerVo.setRedirect(menu.getMenuRedirect());
            routerVo.setComponent(String.format("..%s", menu.getMenuViewPath()));
            MetaVo metaVo = new MetaVo();
            metaVo.setTitle(menu.getMenuName());
            metaVo.setIcon(menu.getMenuIcon());
            metaVo.setMenuType(menu.getMenuType());
            metaVo.setActiveMenu(menu.getMenuActive());
            metaVo.setLinkStatus(Objects.equals(STATUS_SUCCESS, menu.getMenuLink()));
            metaVo.setHiddenStatus(Objects.equals(STATUS_ERROR, menu.getMenuHidden()));
            metaVo.setAffixStatus(Objects.equals(STATUS_SUCCESS, menu.getMenuAffix()));
            metaVo.setKeepAliveStatus(Objects.equals(STATUS_SUCCESS, menu.getMenuCache()));
            metaVo.setRoles(StrUtil.splitTrim(menu.getMenuAuthority(), DELIMITER));//perms
            metaVo.setRank(menu.getMenuSort());
            routerVo.setMeta(metaVo);
            result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(BeanUtil.beanToMap(routerVo)));
        }
        return TreeUtils.buildList(result, Boolean.FALSE);
    }

    /**
     * 初始化 策略工厂
     */
    @Override
    public final void afterPropertiesSet() {
        UserRouterFactory.register(getUserType(), this);
    }

}
