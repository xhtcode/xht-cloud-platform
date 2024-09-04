package com.xht.cloud.file.domain.request;

import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：文件元数据信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysMetadataFileQueryRequest(文件元数据信息-查询请求信息)")
public class SysMetadataFileQueryRequest extends PageQueryRequest {

    /**
     * 桶名称
     */
    @Schema(name = "bucket", description = "桶名称")
    private String bucket;

    /**
     * 文件名
     */
    @Schema(name = "fileName", description = "文件名")
    private String fileName;


    /**
     * 文件状态
     */
    @Schema(name = "fileStatus", description = "文件状态")
    private FileStatusEnums fileStatus;
}
