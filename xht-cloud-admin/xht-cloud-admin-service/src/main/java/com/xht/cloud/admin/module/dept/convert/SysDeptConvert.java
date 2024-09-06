package com.xht.cloud.admin.module.dept.convert;

import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptCreateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDeptConvert extends IBaseConvert<SysDeptCreateRequest, SysDeptResponse, SysDeptDO> {

}
