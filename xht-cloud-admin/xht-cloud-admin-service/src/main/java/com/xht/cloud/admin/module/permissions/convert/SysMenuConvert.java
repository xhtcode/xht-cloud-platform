package com.xht.cloud.admin.module.permissions.convert;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：菜单权限
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysMenuConvert extends IBaseConvert<SysMenuCreateRequest, SysMenuResponse, SysMenuDO> {

    /**
     * {@link SysMenuDO} to {@link SysMenuCreateRequest}
     */
    SysMenuCreateRequest toRequest(SysMenuDO menuRequest);

}
