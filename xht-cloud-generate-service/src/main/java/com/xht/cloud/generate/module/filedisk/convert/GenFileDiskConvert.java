package com.xht.cloud.generate.module.filedisk.convert;

import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskCreateRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskUpdateRequest;
import com.xht.cloud.generate.module.filedisk.domain.response.GenFileDiskResponse;
import org.mapstruct.Mapper;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenFileDiskConvert extends IBaseConvert<GenFileDiskCreateRequest, GenFileDiskUpdateRequest, GenFileDiskResponse, GenFileDiskDO> {
}
