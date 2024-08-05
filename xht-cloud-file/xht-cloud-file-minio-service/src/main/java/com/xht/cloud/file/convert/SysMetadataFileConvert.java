package com.xht.cloud.file.convert;

import com.xht.cloud.file.domain.dataobject.SysMetadataFileDO;
import com.xht.cloud.file.domain.response.SysMetadataFileResponse;
import com.xht.cloud.framework.mybatis.convert.PageConvert;
import org.mapstruct.Mapper;

/**
 * 描述 ：文件元数据信息转换
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysMetadataFileConvert extends PageConvert<SysMetadataFileDO, SysMetadataFileResponse> {
}
