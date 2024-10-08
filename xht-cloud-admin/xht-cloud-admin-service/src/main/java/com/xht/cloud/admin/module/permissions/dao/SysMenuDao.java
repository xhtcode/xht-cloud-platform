package com.xht.cloud.admin.module.permissions.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.constant.MenuConstant;
import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.admin.module.permissions.mapper.SysMenuMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述 ：工厂信息
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysMenuDao extends BaseDaoImpl<SysMenuMapper, SysMenuDO> {

    /**
     * 查询菜单路由
     *
     * @return 菜单路由数据
     */
    public List<SysMenuDO> selectMenuRouter() {
        List<String> list = new ArrayList<>();
        list.add(MenuTypeEnums.M.getValue());
        list.add(MenuTypeEnums.C.getValue());
        return getBaseMapper().selectListIn(SysMenuDO::getMenuType, list);
    }


    /**
     * 根据用户查询菜单路由
     *
     * @param userId 用户id
     * @return 菜单路由数据
     */
    public List<SysMenuDO> selectMenuRouter(String userId) {
        List<String> list = new ArrayList<>();
        list.add(MenuTypeEnums.M.getValue());
        list.add(MenuTypeEnums.C.getValue());
        return getBaseMapper().selectByUserIdAndMenuType(userId, list);
    }


    /**
     * 根据用户查询菜单权限编码
     *
     * @param userId 用户id
     * @return 菜单路由数据
     */
    public Set<String> selectMenuAuthority(Serializable userId) {
        // @formatter:off
        Set<String> list = getBaseMapper()
                .selectMenuAuthorityByUserId(userId);
        return Objects.isNull(list) ?
                Collections.emptySet() :
                list.stream().filter(StringUtils::hasText).collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 获取所有的权限编码
     *
     * @return 所有角色code
     */
    public Set<String> selectAllMenuAuthority() {
        // @formatter:off
        LambdaQueryWrapper<SysMenuDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(SysMenuDO::getMenuAuthority)
                .isNotNull(SysMenuDO::getMenuAuthority);
        return list(lambdaQueryWrapper)
                .stream()
                .map(SysMenuDO::getMenuAuthority)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 根据用户名还有菜单类型查询菜单
     *
     * @param userId 用户id
     * @param list   菜单类型
     * @return {@link SysMenuResponse} 菜单数据
     */
    public List<SysMenuDO> selectByUserIdAndMenuType(String userId, List<String> list) {
        return getBaseMapper().selectByUserIdAndMenuType(userId, list);
    }

    /**
     * 判断菜单视图名称是否存在
     *
     * @param menuViewName 菜单视图名称
     * @param menuId       菜单id
     */
    public boolean existsMenuViewName(String menuViewName, String menuId) {
        // @formatter:off
        return exists(lambdaQuery()
                .eq(SysMenuDO::getMenuViewName, menuViewName)
                .eq(SysMenuDO::getMenuLink, MenuConstant.STATUS_ERROR)
                .ne(StringUtils.hasText(menuId), SysMenuDO::getId, menuId));
        // @formatter:on
    }

    /**
     * 判断菜单路由地址是否存在
     *
     * @param menuPath 菜单路由地址
     */
    public boolean existsMenuPath(String menuPath) {
        // @formatter:off
        return exists(lambdaQuery()
                .eq(SysMenuDO::getMenuViewName, menuPath));
        // @formatter:on
    }

    /**
     * 判断是否存在下级菜单
     *
     * @param menuId 菜单id
     * @return true 存在
     */
    public boolean existsMenuChild(String menuId) {
        return exists(lambdaQuery()
                .eq(SysMenuDO::getParentId, menuId));
    }

    /**
     * 判断是否存在下级菜单
     *
     * @param menuIds 菜单id
     * @return true 存在
     */
    public boolean existsMenuChild(List<String> menuIds) {
        return exists(lambdaQuery()
                .in(SysMenuDO::getParentId, menuIds));
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysMenuUpdateRequest updateRequest) {
        LambdaUpdateWrapper<SysMenuDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(SysMenuDO::getParentId, updateRequest.getParentId())
                .set(SysMenuDO::getMenuType, updateRequest.getMenuType())
                .set(SysMenuDO::getMenuName, updateRequest.getMenuName())
                .set(SysMenuDO::getMenuPath, updateRequest.getMenuPath())
                .set(SysMenuDO::getMenuViewName, updateRequest.getMenuViewName())
                .set(SysMenuDO::getMenuViewPath, updateRequest.getMenuViewPath())
                .set(SysMenuDO::getMenuIcon, updateRequest.getMenuIcon())
                .set(SysMenuDO::getMenuRedirect, updateRequest.getMenuRedirect())
                .set(SysMenuDO::getMenuActive, updateRequest.getMenuActive())
                .set(SysMenuDO::getMenuAuthority, updateRequest.getMenuAuthority())
                .set(SysMenuDO::getMenuHidden, updateRequest.getMenuHidden())
                .set(SysMenuDO::getMenuStatus, updateRequest.getMenuStatus())
                .set(SysMenuDO::getMenuLink, updateRequest.getMenuLink())
                .set(SysMenuDO::getMenuCache, updateRequest.getMenuCache())
                .set(SysMenuDO::getMenuAffix, updateRequest.getMenuAffix())
                .set(SysMenuDO::getMenuSort, updateRequest.getMenuSort())
                .eq(SysMenuDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
