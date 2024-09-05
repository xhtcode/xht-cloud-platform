package com.xht.cloud.generate.module.filedisk.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "GenFileDiskUpdateRequest", description = "文件管理")
public class GenFileDiskUpdateRequest extends GenFileDiskCreateRequest {

    /**
     * id
     */
    @Schema(description = "主键")
    private String id;

}
