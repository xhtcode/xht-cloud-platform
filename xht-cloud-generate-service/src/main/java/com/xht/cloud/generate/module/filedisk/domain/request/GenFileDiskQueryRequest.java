package com.xht.cloud.generate.module.filedisk.domain.request;

import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "GenFileDiskQueryRequest", description = "文件管理")
public class GenFileDiskQueryRequest extends PageQueryRequest {

    /**
     * 上级目录
     */
    @Schema(description = "上级目录")
    private Long parentId;

    /**
     * 配置id
     */
    @NotEmpty(message = "配置id不能为空")
    @Schema(description = "配置id")
    private String configId;


    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String filePath;

    private String notFileType;
}