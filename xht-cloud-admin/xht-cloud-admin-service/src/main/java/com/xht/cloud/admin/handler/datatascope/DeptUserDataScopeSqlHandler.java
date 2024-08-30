package com.xht.cloud.admin.handler.datatascope;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.exceptions.PermissionException;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMapper;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlFactory;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlHandler;
import com.xht.cloud.framework.mybatis.handler.dto.DataScopeDTO;
import com.xht.cloud.framework.mybatis.tool.SqlGenerator;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述 ：部门用户级别数据权限
 *
 * @author 小糊涂
 **/
@Service
public class DeptUserDataScopeSqlHandler extends DataScopeSqlHandler<DeptUserDataScopeEnum> {

    private final SysRoleMapper sysRoleMapper;

    private final SysDeptMapper sysDeptMapper;

    public DeptUserDataScopeSqlHandler(DataScopeSqlFactory dataScopeFactory, SysRoleMapper sysRoleMapper, SysDeptMapper sysDeptMapper) {
        super(dataScopeFactory);
        this.sysRoleMapper = sysRoleMapper;
        this.sysDeptMapper = sysDeptMapper;
    }

    /**
     * 权限前置校验 并且返回校验后的信息
     *
     * @return {@link DataScopeDTO}
     */
    @Override
    public DataScopeDTO<DeptUserDataScopeEnum> verify() {
        if (SecurityContextUtil.isAdmin()) {
            return DataScopeDTO.<DeptUserDataScopeEnum>builder().verify(false).build();
        }
        UserDetailsBO user = SecurityContextUtil.user().orElseThrow(() -> new PermissionException("未查询到登录用户信息！"));
        DeptUserDataScopeEnum dataScope = user.getDataScope();
        if (Objects.isNull(dataScope)) {
            throw new PermissionException("部门级权限不足，请联系管理员重新分配部门级数据权限！");
        }
        if (Objects.equals(DeptUserDataScopeEnum.DATA_SCOPE_ALL, dataScope)) {
            return DataScopeDTO.<DeptUserDataScopeEnum>builder().verify(false).build();
        }
        String deptId = user.getDeptId();
        if (!StringUtils.hasText(deptId)) {
            throw new PermissionException("未分配部门，请联系管理员！");
        }
        return DataScopeDTO.<DeptUserDataScopeEnum>builder().verify(true).userId(user.getId()).deptId(deptId).dataScope(dataScope)
                .build();
    }

    /**
     * @param dataScopeDTO       数据权限前置校验后返回的信息
     * @param builder            字段构建用户获取权限字段的name
     * @param lambdaQueryWrapper sql构建器
     */
    @Override
    public <DO extends AbstractDO> void generate(DataScopeDTO<DeptUserDataScopeEnum> dataScopeDTO, DataScopeFieldBuilder<DO> builder, LambdaQueryWrapper<DO> lambdaQueryWrapper) {
        String deptId = dataScopeDTO.getDeptId();
        String userId = dataScopeDTO.getUserId();
        DeptUserDataScopeEnum deptUserDataScopeEnum = dataScopeDTO.getDataScope();
        List<String> resultList = new ArrayList<>();
        switch (Objects.requireNonNull(deptUserDataScopeEnum)) {
            case DATA_SCOPE_CUSTOM -> { // 自定数据权限
                resultList = sysRoleMapper.selectDeptIdByDataScope(userId, deptUserDataScopeEnum.getValue());
                if (CollectionUtils.isEmpty(resultList)) {
                    throw new PermissionException("部门级权限不足，请联系管理员重新分配部门级数据权限！");
                }
                SqlGenerator.generateInClause(builder.getDeptField(), resultList, lambdaQueryWrapper);
            }
            case DATA_SCOPE_DEPT_AND_CHILD -> { // 本部门及以下数据权限
                List<SysDeptDO> sysDeptDOS = sysDeptMapper.selectChildByDeptId(deptId);
                resultList = sysDeptDOS.stream().map(SysDeptDO::getId).collect(Collectors.toList());
                SqlGenerator.generateInClause(builder.getDeptField(), resultList, lambdaQueryWrapper);
            }
            case DATA_SCOPE_DEPT -> { // 本部门数据权限
                resultList.add(deptId);
                SqlGenerator.generateInClause(builder.getDeptField(), resultList, lambdaQueryWrapper);
            }
            case DATA_SCOPE_SELF -> { //本人数据
                SFunction<DO, ?> userField = builder.getUserField();
                if (Objects.isNull(userField)) {
                    lambdaQueryWrapper.eq(builder.getDeptField(), deptId);
                } else {
                    lambdaQueryWrapper.eq(builder.getUserField(), userId);
                }
            }
        }
    }

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @param queryWrapper sql构建器
     */
    @Override
    public <DO extends AbstractDO> void generate(DataScopeDTO<DeptUserDataScopeEnum> dataScopeDTO, DataScopeFieldBuilder<DO> builder, QueryWrapper<DO> queryWrapper) {
        String deptId = dataScopeDTO.getDeptId();
        String userId = dataScopeDTO.getUserId();
        DeptUserDataScopeEnum deptUserDataScopeEnum = dataScopeDTO.getDataScope();
        List<String> resultList = new ArrayList<>();
        switch (Objects.requireNonNull(deptUserDataScopeEnum)) {
            case DATA_SCOPE_CUSTOM -> { // 自定数据权限
                resultList = sysRoleMapper.selectDeptIdByDataScope(userId, deptUserDataScopeEnum.getValue());
                if (CollectionUtils.isEmpty(resultList)) {
                    throw new PermissionException("部门级权限不足，请联系管理员重新分配部门级数据权限！");
                }
                SqlGenerator.generateInClause(builder.getDeptStrField(), resultList, queryWrapper);
            }
            case DATA_SCOPE_DEPT_AND_CHILD -> { // 本部门及以下数据权限
                List<SysDeptDO> sysDeptDOS = sysDeptMapper.selectChildByDeptId(deptId);
                resultList = sysDeptDOS.stream().map(SysDeptDO::getId).collect(Collectors.toList());
                SqlGenerator.generateInClause(builder.getDeptStrField(), resultList, queryWrapper);
            }
            case DATA_SCOPE_DEPT -> { // 本部门数据权限
                resultList.add(deptId);
                SqlGenerator.generateInClause(builder.getDeptStrField(), resultList, queryWrapper);
            }
            case DATA_SCOPE_SELF -> { //本人数据
                String userField = builder.getUserStrField();
                if (Objects.isNull(userField)) {
                    queryWrapper.eq(builder.getDeptStrField(), deptId);
                } else {
                    queryWrapper.eq(builder.getUserStrField(), userId);
                }
            }
        }
    }

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @return sql 字符串
     */
    @Override
    public <DO extends AbstractDO> String generate(DataScopeDTO<DeptUserDataScopeEnum> dataScopeDTO, DataScopeFieldBuilder<DO> builder) {
        String deptId = dataScopeDTO.getDeptId();
        String userId = dataScopeDTO.getUserId();
        DeptUserDataScopeEnum deptUserDataScopeEnum = dataScopeDTO.getDataScope();
        List<String> resultList = new ArrayList<>();
        switch (Objects.requireNonNull(deptUserDataScopeEnum)) {
            case DATA_SCOPE_CUSTOM -> { // 自定数据权限
                resultList = sysRoleMapper.selectDeptIdByDataScope(userId, deptUserDataScopeEnum.getValue());
                if (CollectionUtils.isEmpty(resultList)) {
                    throw new PermissionException("部门级权限不足，请联系管理员重新分配部门级数据权限！");
                }
            }
            case DATA_SCOPE_DEPT_AND_CHILD -> { // 本部门及以下数据权限
                List<SysDeptDO> sysDeptDOS = sysDeptMapper.selectChildByDeptId(deptId);
                resultList = sysDeptDOS.stream().map(SysDeptDO::getId).collect(Collectors.toList());
            }
            case DATA_SCOPE_DEPT -> // 本部门数据权限
                    resultList.add(deptId);
            case DATA_SCOPE_SELF -> { //本人数据
                return SqlGenerator.generateInClause(builder.getUserStrField(), resultList);
            }
        }
        return SqlGenerator.generateInClause(builder.getDeptStrField(), resultList);
    }


    @Override
    public void afterPropertiesSet() {
        dataScopeFactory.put(DataScopeTypeEnums.DEPT_USER_TYPE, this);
    }
}
