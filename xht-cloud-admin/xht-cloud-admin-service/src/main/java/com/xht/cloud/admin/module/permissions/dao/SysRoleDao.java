package com.xht.cloud.admin.module.permissions.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述 ：角色
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysRoleDao extends BaseDaoImpl<SysRoleMapper, SysRoleDO> {

    /**
     * 根据用户id查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return 该用户所拥有的角色
     */
    public List<SysRoleDO> getRoleList(Serializable userId) {
        return getBaseMapper().selectListByUserId(userId);
    }

    /**
     * 根据用户id查询该用户所拥有的角色code
     *
     * @param userId 用户id
     * @return 该用户所拥有的角色
     */
    public Set<String> getRoleCode(Serializable userId) {
        Set<String> list = getBaseMapper().selectRoleCodeByUserId(userId);
        return Objects.isNull(list) ? Collections.emptySet() : list.stream().filter(StringUtils::hasText).collect(Collectors.toSet());
    }

    /**
     * 获取所有的角色code
     *
     * @return 所有角色code
     */
    public Set<String> getAllRoleCode() {
        LambdaQueryWrapper<SysRoleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysRoleDO::getRoleCode).isNotNull(SysRoleDO::getRoleCode);
        return list(lambdaQueryWrapper).stream().map(SysRoleDO::getRoleCode).filter(StringUtils::hasText).collect(Collectors.toSet());
    }
}
