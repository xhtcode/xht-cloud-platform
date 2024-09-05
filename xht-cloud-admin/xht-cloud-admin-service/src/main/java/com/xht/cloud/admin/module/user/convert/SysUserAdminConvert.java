package com.xht.cloud.admin.module.user.convert;

import com.xht.cloud.admin.module.user.domain.dataobject.SysUserAdminDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserAdminResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysUserAdminConvert extends IBaseConvert<SysUserAdminCreateRequest, SysUserAdminUpdateRequest, SysUserAdminResponse, SysUserAdminDO> {
}
