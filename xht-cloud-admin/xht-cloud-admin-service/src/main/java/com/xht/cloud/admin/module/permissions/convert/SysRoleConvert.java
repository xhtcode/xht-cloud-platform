package com.xht.cloud.admin.module.permissions.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysRoleResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysRoleConvert extends IBaseConvert<SysRoleCreateRequest, SysRoleUpdateRequest, SysRoleQueryRequest, SysRoleResponse, SysRoleDO>, EntityWrapper<SysRoleDO> {

    /**
     * {@link SysRoleCreateRequest} to {@link SysRoleDO}
     */
    @Override
    @Named(value = "addRequestToDo")
    @Mapping(target = "dataScope", expression = "java(com.xht.cloud.framework.mybatis.enums.DeptUserDataScopeEnum.getDataScope(createRequest.getDataScope()))")
    SysRoleDO toDO(SysRoleCreateRequest createRequest);

    /**
     * {@link SysRoleUpdateRequest} to {@link SysRoleDO}
     */
    @Override
    @Named(value = "updateRequestToDo")
    @Mapping(target = "dataScope", expression = "java(com.xht.cloud.framework.mybatis.enums.DeptUserDataScopeEnum.getDataScope(updateRequest.getDataScope()))")
    SysRoleDO toDO(SysRoleUpdateRequest updateRequest);

    /**
     * {@link SysRoleQueryRequest} to {@link SysRoleDO}
     */
    @Override
    @Named(value = "queryRequestToDo")
    @Mapping(target = "dataScope", expression = "java(com.xht.cloud.framework.mybatis.enums.DeptUserDataScopeEnum.getDataScope(queryRequest.getDataScope()))")
    SysRoleDO toDO(SysRoleQueryRequest queryRequest);

    /**
     * {@link SysRoleDO} to {@link SysRoleResponse}
     */
    @Override
    @Named(value = "DoToResponse")
    @Mapping(target = "dataScope", nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION, expression = "java(null != testDO.getDataScope() ?  testDO.getDataScope().getValue() : null)")
    SysRoleResponse toResponse(SysRoleDO testDO);


    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysRoleDO> lambdaQuery(SysRoleDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysRoleDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysRoleDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getRoleName()), SysRoleDO::getRoleName, entity.getRoleName())
                .eq(StringUtils.hasText(entity.getRoleCode()), SysRoleDO::getRoleCode, entity.getRoleCode())
                .eq(!ObjectUtils.isEmpty(entity.getRoleSort()), SysRoleDO::getRoleSort, entity.getRoleSort())
                .eq(StringUtils.hasText(entity.getStatus()), SysRoleDO::getStatus, entity.getStatus())
                .eq(StringUtils.hasText(entity.getRoleDesc()), SysRoleDO::getRoleDesc, entity.getRoleDesc())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysRoleDO> lambdaUpdate(SysRoleDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysRoleDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysRoleDO::getRoleName, entity.getRoleName())
                .set(SysRoleDO::getRoleCode, entity.getRoleCode())
                .set(SysRoleDO::getRoleSort, entity.getRoleSort())
                .set(SysRoleDO::getStatus, entity.getStatus())
                .set(SysRoleDO::getRoleDesc, entity.getRoleDesc())
                ;
    }


}
