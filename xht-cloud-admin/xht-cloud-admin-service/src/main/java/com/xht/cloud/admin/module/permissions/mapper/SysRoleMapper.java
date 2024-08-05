package com.xht.cloud.admin.module.permissions.mapper;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRoleDO> {

    /**
     * 根据用户id查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return 该用户所拥有的角色
     */
    List<SysRoleDO> selectListByUserId(@Param("userId") String userId);

    /**
     * 根据用户id 查询最大的数据权限
     *
     * @param userId 用户id
     * @return
     */
    Integer selectDataScopeByUserId(@Param("userId") String userId);

    /**
     * 根据用户id 和用户数据权限级别查询所拥有的角色id
     *
     * @param userId      用户id
     * @param dataScopeId 数据权限级别
     * @return 数据权限级别查询所拥有的角色id
     */
    List<String> selectDeptIdByDataScope(@Param("userId") String userId, @Param("dataScopeId") Integer dataScopeId);
}
