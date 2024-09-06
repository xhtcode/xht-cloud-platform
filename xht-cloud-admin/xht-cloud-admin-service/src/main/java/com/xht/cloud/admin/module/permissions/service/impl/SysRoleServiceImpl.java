package com.xht.cloud.admin.module.permissions.service.impl;

import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.constant.CommonStatus;
import com.xht.cloud.admin.exceptions.PermissionException;
import com.xht.cloud.admin.module.dept.convert.SysDeptConvert;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysRoleDeptDO;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.admin.module.permissions.convert.SysRoleConvert;
import com.xht.cloud.admin.module.permissions.dao.SysRoleDao;
import com.xht.cloud.admin.module.permissions.dao.SysRoleDeptDao;
import com.xht.cloud.admin.module.permissions.dao.SysUserRoleDao;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysRoleResponse;
import com.xht.cloud.admin.module.permissions.service.ISysRoleService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
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

    private final SysRoleDao sysRoleDao;

    private final SysDeptMapper sysDeptMapper;

    private final SysRoleDeptDao sysRoleDeptDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleConvert sysRoleConvert;

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
        if (sysRoleDao.existsRoleCode(createRequest.getRoleCode())) {
            throw new BizException("角色编码`{}`不能重复", createRequest.getRoleCode());
        }
        SysRoleDO entity = sysRoleConvert.toDO(createRequest);
        sysRoleDao.save(entity);
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
        Assert.hasText(updateRequest.getId(), "角色修改信息id不能为空");
        if (sysRoleDao.existsRoleCode(updateRequest.getId(),updateRequest.getRoleCode())) {
            throw new BizException("角色编码`{}`不能重复", updateRequest.getRoleCode());
        }
        sysRoleDeptDao.remove(sysRoleDeptDao.lambdaQuery().eq(SysRoleDeptDO::getRoleId, updateRequest.getId()));
        saveRoleDept(updateRequest.getId(), DeptUserDataScopeEnum.DATA_SCOPE_CUSTOM, updateRequest.getDeptIds());
        sysRoleDao.updateRequest(updateRequest);
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
        if (sysUserRoleDao.existsRoleId(ids)) {
            throw new PermissionException("该角色已绑定用户，请勿操作");
        }
        sysRoleDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询角色详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysRoleResponse}
     */
    @Override
    public SysRoleResponse findById(String id) {
        SysRoleResponse response = sysRoleConvert.toResponse(sysRoleDao.getById(id));
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
        return sysRoleConvert.toPageResponse(sysRoleDao.pageQueryRequest(queryRequest));
    }

    /**
     * 查询全部角色
     *
     * @param queryRequest {@link SysRoleQueryRequest}
     * @return {@link List<SysRoleResponse>} 详情
     */
    @Override
    public List<SysRoleResponse> list(SysRoleQueryRequest queryRequest) {
        return sysRoleConvert.toResponse(sysRoleDao.listQueryRequest(queryRequest));
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
                    sysRoleDeptDao.saveBatch(deptDOS);
                }
            }
            case DATA_SCOPE_SELF -> { //本人数据
                SysRoleDeptDO sysRoleDeptDO = new SysRoleDeptDO();
                sysRoleDeptDO.setRoleId(roleId);
                sysRoleDeptDO.setDeptId(CommonStatus.DEPT_PARENT_ID);
                sysRoleDeptDao.save(sysRoleDeptDO);
            }
        }

    }

}
