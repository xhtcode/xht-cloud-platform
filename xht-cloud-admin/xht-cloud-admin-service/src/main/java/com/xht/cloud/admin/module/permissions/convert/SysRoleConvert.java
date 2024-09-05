package com.xht.cloud.admin.module.permissions.convert;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysRoleResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysRoleConvert extends IBaseConvert<SysRoleCreateRequest, SysRoleUpdateRequest, SysRoleResponse, SysRoleDO> {

    /**
     * {@link SysRoleCreateRequest} to {@link SysRoleDO}
     */
    @Override
    @Named(value = "addRequestToDo")
    SysRoleDO toDO(SysRoleCreateRequest createRequest);

    /**
     * {@link SysRoleUpdateRequest} to {@link SysRoleDO}
     */
    @Override
    @Named(value = "updateRequestToDo")
    SysRoleDO toDO(SysRoleUpdateRequest updateRequest);

    /**
     * {@link SysRoleQueryRequest} to {@link SysRoleDO}
     */
    @Named(value = "queryRequestToDo")
    SysRoleDO toDO(SysRoleQueryRequest queryRequest);

    /**
     * {@link SysRoleDO} to {@link SysRoleResponse}
     */
    @Override
    @Named(value = "DoToResponse")
    SysRoleResponse toResponse(SysRoleDO testDO);


}
