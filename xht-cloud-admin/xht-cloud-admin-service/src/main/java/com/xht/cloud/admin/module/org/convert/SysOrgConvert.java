package com.xht.cloud.admin.module.org.convert;

import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.admin.module.org.domain.dataobject.SysOrgDO;
import com.xht.cloud.admin.module.org.domain.request.SysOrgCreateRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgQueryRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgUpdateRequest;
import com.xht.cloud.admin.module.org.domain.response.SysOrgResponse;
import org.mapstruct.Mapper;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Mapper(componentModel = "spring")
public interface SysOrgConvert extends IBaseConvert<SysOrgCreateRequest, SysOrgUpdateRequest, SysOrgResponse, SysOrgDO> {

}
