package com.xht.cloud.admin.module.user.convert;

import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserStaffResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysUserStaffConvert extends IBaseConvert<SysUserStaffCreateRequest, SysUserStaffResponse, SysUserStaffDO> {


    SysUserStaffDO convert(UserUpdateRequest userUpdateRequest);
}
