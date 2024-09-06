package com.xht.cloud.admin.module.sequence.convertor;

import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysSequenceConvertor extends IBaseConvert<SysSequenceCreateRequest, SysSequenceResponse, SysSequenceDO> {

}
