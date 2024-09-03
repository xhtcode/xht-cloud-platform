package com.xht.cloud.admin.module.permissions.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.mapper.SysMenuMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.utils.support.StringUtils;
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
        Set<String> list = getBaseMapper().selectMenuAuthorityByUserId(userId);
        return Objects.isNull(list) ? Collections.emptySet() : list.stream().filter(StringUtils::hasText).collect(Collectors.toSet());
    }

    /**
     * 获取所有的权限编码
     *
     * @return 所有角色code
     */
    public Set<String> selectAllMenuAuthority() {
        LambdaQueryWrapper<SysMenuDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysMenuDO::getMenuAuthority).isNotNull(SysMenuDO::getMenuAuthority);
        return list(lambdaQueryWrapper).stream().map(SysMenuDO::getMenuAuthority).filter(StringUtils::hasText).collect(Collectors.toSet());
    }

    /**
     * 根据主键修改数据
     *
     * @param entity 实体
     * @return 修改
     */
    @Override
    public boolean update(SysMenuDO entity) {
        return false;
    }

}
