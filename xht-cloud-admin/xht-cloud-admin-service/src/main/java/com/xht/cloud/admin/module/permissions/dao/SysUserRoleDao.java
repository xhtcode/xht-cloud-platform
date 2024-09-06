package com.xht.cloud.admin.module.permissions.dao;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysUserRoleDO;
import com.xht.cloud.admin.module.permissions.mapper.SysUserRoleMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysUserRoleDao extends BaseDaoImpl<SysUserRoleMapper, SysUserRoleDO> {

    /**
     * 跟据用户id删除
     *
     * @param userId 用户id
     * @return 删除成功true
     */
    public void removeByUserId(String userId) {
        remove(lambdaQuery().eq(SysUserRoleDO::getUserId, userId));
    }

    /**
     * 根据用户id 查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return 角色id
     */
    public List<String> selectRoleIdList(String userId) {
        // @formatter:off
        List<SysUserRoleDO> userRoleDOS = list(
                lambdaQuery()
                        .select(SysUserRoleDO::getRoleId)
                        .eq(SysUserRoleDO::getUserId, userId)
        );
        if (CollectionUtils.isEmpty(userRoleDOS)) {
            return Collections.emptyList();
        }
        // @formatter:on
        return userRoleDOS.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toList());
    }

    /**
     * 判断角色是否已经与用户绑定
     *
     * @param roleIds 角色id
     * @return 存在true
     */
    public boolean existsRoleId(List<String> roleIds) {
        return exists(lambdaQuery().in(SysUserRoleDO::getRoleId, roleIds));
    }

    /**
     * 判断角色是否已经与用户绑定
     *
     * @param roleId 角色id
     * @return 存在true
     */
    public boolean existsRoleId(String roleId) {
        return exists(lambdaQuery().eq(SysUserRoleDO::getRoleId, roleId));
    }
}
