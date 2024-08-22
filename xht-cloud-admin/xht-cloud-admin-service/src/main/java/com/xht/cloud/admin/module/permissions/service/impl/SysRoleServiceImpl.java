package com.xht.cloud.admin.module.permissions.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.constant.CommonStatus;
import com.xht.cloud.admin.exceptions.PermissionException;
import com.xht.cloud.admin.module.dept.convert.SysDeptConvert;
import com.xht.cloud.admin.module.dept.convert.SysRoleDeptConvert;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysRoleDeptDO;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.admin.module.dept.mapper.SysRoleDeptMapper;
import com.xht.cloud.admin.module.permissions.convert.SysRoleConvert;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysUserRoleDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysRoleResponse;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMapper;
import com.xht.cloud.admin.module.permissions.mapper.SysUserRoleMapper;
import com.xht.cloud.admin.module.permissions.service.ISysRoleService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.enums.DeptUserDataScopeEnum;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysDeptMapper sysDeptMapper;

    private final SysRoleDeptMapper sysRoleDeptMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysRoleConvert sysRoleConvert;

    private final SysRoleDeptConvert sysRoleDeptConvert;

    private final SysDeptConvert sysDeptConvert;

    /**
     * 创建角色
     *
     * @param createRequest {@link SysRoleCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysRoleCreateRequest createRequest) {
        Assert.notNull(createRequest, "角色添加信息不能为空");
        long l = sysRoleMapper.selectCount(SysRoleDO::getRoleCode, createRequest.getRoleCode());
        if (l > 0) {
            throw new BizException(String.format("角色编码`%s`不能重复",createRequest.getRoleCode()));
        }
        SysRoleDO entity = sysRoleConvert.toDO(createRequest);
        sysRoleMapper.insert(entity);
        saveRoleDept(entity.getId(), DeptUserDataScopeEnum.DATA_SCOPE_CUSTOM, createRequest.getDeptIds());
        return entity.getId();
    }

    /**
     * 根据id修改角色
     *
     * @param updateRequest SysRoleUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "角色修改信息不能为空");
        Assert.hasText(updateRequest.getPkId(), "角色修改信息id不能为空");
        LambdaQueryWrapper<SysRoleDO> countQuery = sysRoleConvert.lambdaQuery()
                .eq(SysRoleDO::getRoleCode, updateRequest.getRoleCode())
                .ne(SysRoleDO::getId,updateRequest.getPkId());
        long l = sysRoleMapper.selectCount(countQuery);
        if (l > 0) {
            throw new BizException(String.format("角色编码`%s`不能重复",updateRequest.getRoleCode()));
        }
        SysRoleDO entity = sysRoleConvert.toDO(updateRequest);
        sysRoleDeptMapper.delete(sysRoleDeptConvert.lambdaQuery().eq(SysRoleDeptDO::getRoleId, entity.getId()));
        saveRoleDept(updateRequest.getId(), DeptUserDataScopeEnum.DATA_SCOPE_CUSTOM, updateRequest.getDeptIds());
        sysRoleMapper.updateById(entity);
        // @formatter:on
    }

    /**
     * 删除角色
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        // @formatter:off
        LambdaQueryWrapper<SysRoleDO> lambdaQueryWrapper = sysRoleConvert.lambdaQuery()
                .select(
                        SysRoleDO::getId,
                        SysRoleDO::getStatus
                )
                .in(SysRoleDO::getId, ids);
        List<SysRoleDO> sysRoleDOS = sysRoleMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(ids) || sysRoleDOS.size() != ids.size()) {
            throw new PermissionException("业务异常删除失败！");
        }
        if ( sysUserRoleMapper.selectCountIn(SysUserRoleDO::getRoleId, ids) > 0) {
            throw new PermissionException("该角色已绑定用户，请勿操作");
        }
        sysRoleMapper.deleteBatchIds(ids);
        // @formatter:on
    }

    /**
     * 根据id查询角色详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysRoleResponse}
     */
    @Override
    public SysRoleResponse findById(String id) {
        SysRoleResponse response = sysRoleConvert.toResponse(sysRoleMapper.findById(id).orElse(null));
        if (Objects.nonNull(response)) {
            List<SysDeptDO> sysDeptDOS = sysDeptMapper.selectDeptByRoleId(id);
            response.setDepts(sysDeptConvert.toResponse(sysDeptDOS));
        }
        return response;
    }

    /**
     * 分页查询角色
     *
     * @param queryRequest {@link SysRoleQueryRequest}
     * @return {@link SysRoleResponse} 分页详情
     */
    @Override
    public PageResponse<SysRoleResponse> findPage(SysRoleQueryRequest queryRequest) {
        IPage<SysRoleDO> sysRoleIPage = sysRoleMapper.selectPage(PageTool.getPage(queryRequest),
                sysRoleConvert.lambdaQuery(sysRoleConvert.toDO(queryRequest)));
        return sysRoleConvert.toPageResponse(sysRoleIPage);
    }

    /**
     * 查询全部角色
     *
     * @param queryRequest {@link SysRoleQueryRequest}
     * @return {@link List<SysRoleResponse>} 详情
     */
    @Override
    public List<SysRoleResponse> list(SysRoleQueryRequest queryRequest) {
        List<SysRoleDO> sysRoleIPage = sysRoleMapper.selectList(sysRoleConvert.lambdaQuery(sysRoleConvert.toDO(queryRequest)));
        return sysRoleConvert.toResponse(sysRoleIPage);
    }


    /**
     * 保存角色与部门关系
     *
     * @param roleId                角色id
     * @param deptUserDataScopeEnum 数据类型
     * @param deptIds               部门id
     */
    public void saveRoleDept(String roleId, DeptUserDataScopeEnum deptUserDataScopeEnum, List<String> deptIds) {
        switch (deptUserDataScopeEnum) {
            case DATA_SCOPE_CUSTOM -> {
                if (!CollectionUtils.isEmpty(deptIds)) {
                    List<SysRoleDeptDO> deptDOS = new ArrayList<>();
                    SysRoleDeptDO sysRoleDeptDO;
                    for (String item : deptIds) {
                        sysRoleDeptDO = new SysRoleDeptDO();
                        sysRoleDeptDO.setRoleId(roleId);
                        sysRoleDeptDO.setDeptId(item);
                        deptDOS.add(sysRoleDeptDO);
                    }
                    sysRoleDeptMapper.insertBatch(deptDOS);
                }
            }
            case DATA_SCOPE_SELF -> { //本人数据
                SysRoleDeptDO sysRoleDeptDO = new SysRoleDeptDO();
                sysRoleDeptDO.setRoleId(roleId);
                sysRoleDeptDO.setDeptId(CommonStatus.DEPT_PARENT_ID);
                sysRoleDeptMapper.insert(sysRoleDeptDO);
            }
        }

    }

}
