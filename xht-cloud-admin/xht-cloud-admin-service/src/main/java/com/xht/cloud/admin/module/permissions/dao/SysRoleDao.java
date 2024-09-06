package com.xht.cloud.admin.module.permissions.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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
        // @formatter:off
        Set<String> list = getBaseMapper()
                .selectRoleCodeByUserId(userId);
        return Objects.isNull(list) ?
                Collections.emptySet() : list
                        .stream()
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 获取所有的角色code
     *
     * @return 所有角色code
     */
    public Set<String> getAllRoleCode() {
        LambdaQueryWrapper<SysRoleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        lambdaQueryWrapper
                .select(SysRoleDO::getRoleCode)
                .isNotNull(SysRoleDO::getRoleCode);
        return list(lambdaQueryWrapper)
                .stream()
                .map(SysRoleDO::getRoleCode)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 判断角色编码是否存在
     *
     * @param roleCode 角色编码
     * @return 存在true
     */
    public boolean existsRoleCode(String roleCode) {
        return exists(lambdaQuery().eq(SysRoleDO::getRoleCode, roleCode));
    }

    /**
     * 判断角色编码是否存在 不包括自身
     *
     * @param roleId   角色id
     * @param roleCode 角色编码
     * @return 存在true
     */
    public boolean existsRoleCode(String roleId, String roleCode) {
        // @formatter:off
        return exists(
                lambdaQuery()
                        .ne(SysRoleDO::getId, roleId)
                        .eq(SysRoleDO::getRoleCode, roleCode)
        );
        // @formatter:on
    }

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<SysRoleDO> pageQueryRequest(SysRoleQueryRequest queryRequest) {
        LambdaQueryWrapper<SysRoleDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        wrapper
                .like(StringUtils.hasText(queryRequest.getRoleName()), SysRoleDO::getRoleName, queryRequest.getRoleName())
                .like(StringUtils.hasText(queryRequest.getRoleCode()), SysRoleDO::getRoleCode, queryRequest.getRoleCode())
                .eq(!ObjectUtils.isEmpty(queryRequest.getRoleSort()), SysRoleDO::getRoleSort, queryRequest.getRoleSort())
                .eq(StringUtils.hasText(queryRequest.getStatus()), SysRoleDO::getStatus, queryRequest.getStatus())
                .eq(StringUtils.hasText(queryRequest.getRoleDesc()), SysRoleDO::getRoleDesc, queryRequest.getRoleDesc());
        // @formatter:on
        return page(PageTool.getPage(queryRequest), wrapper);
    }

    /**
     * 查询全部
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public List<SysRoleDO> listQueryRequest(SysRoleQueryRequest queryRequest) {
        LambdaQueryWrapper<SysRoleDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        wrapper
                .like(StringUtils.hasText(queryRequest.getRoleName()), SysRoleDO::getRoleName, queryRequest.getRoleName())
                .like(StringUtils.hasText(queryRequest.getRoleCode()), SysRoleDO::getRoleCode, queryRequest.getRoleCode())
                .eq(!ObjectUtils.isEmpty(queryRequest.getRoleSort()), SysRoleDO::getRoleSort, queryRequest.getRoleSort())
                .eq(StringUtils.hasText(queryRequest.getStatus()), SysRoleDO::getStatus, queryRequest.getStatus())
                .eq(StringUtils.hasText(queryRequest.getRoleDesc()), SysRoleDO::getRoleDesc, queryRequest.getRoleDesc());
        // @formatter:on
        return list(wrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysRoleUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysRoleDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(SysRoleDO::getRoleName, updateRequest.getRoleName())
                .set(SysRoleDO::getRoleCode, updateRequest.getRoleCode())
                .set(SysRoleDO::getRoleSort, updateRequest.getRoleSort())
                .set(SysRoleDO::getStatus, updateRequest.getStatus())
                .set(SysRoleDO::getRoleDesc, updateRequest.getRoleDesc())
                .eq(SysRoleDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
