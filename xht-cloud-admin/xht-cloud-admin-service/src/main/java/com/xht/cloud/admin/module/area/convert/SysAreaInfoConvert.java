package com.xht.cloud.admin.module.area.convert;

import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoCreateRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.domain.response.SysAreaInfoResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysAreaInfoConvert extends IBaseConvert<SysAreaInfoCreateRequest, SysAreaInfoUpdateRequest, SysAreaInfoResponse, SysAreaInfoDO> {

    /**
     * {@link SysAreaInfoDO} to {@link SysAreaInfoResponse}
     */
    @Named(value = "DoToResponse")
    @Mapping(target = "leaf", expression = "java(com.xht.cloud.admin.module.area.convert.AreaLeafConvert.convert(testDO.getLeaf()))")
    SysAreaInfoResponse toResponse(SysAreaInfoDO testDO);
}
