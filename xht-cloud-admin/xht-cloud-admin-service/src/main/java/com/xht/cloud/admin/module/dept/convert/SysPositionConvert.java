package com.xht.cloud.admin.module.dept.convert;

import com.xht.cloud.admin.module.dept.domain.dataobject.SysPositionDO;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionCreateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysPositionResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysPositionConvert extends IBaseConvert<SysPositionCreateRequest, SysPositionResponse, SysPositionDO> {

}
