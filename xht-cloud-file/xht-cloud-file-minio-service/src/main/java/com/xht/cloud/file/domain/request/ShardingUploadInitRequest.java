package com.xht.cloud.file.domain.request;

import com.xht.cloud.framework.core.domain.request.Request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 描述 ：文件分片上传初始化信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "ShardingUploadInitRequest(文件分片上传初始化信息)")
public class ShardingUploadInitRequest extends Request {

    /**
     * 上传id
     */
    @Schema(description = "上传id")
    private String uploadId;

    /**
     * 文件上传的名称
     */
    @NotEmpty(message = "文件上传时的名称不合法")
    @Schema(description = "文件上传的名称")
    private String originalFileName;

    /**
     * 文件总大小
     */
    @NotNull(message = "文件总大小不合法")
    @Schema(description = "文件总大小")
    private Long fileSizeCount;

    /**
     * 文件类型
     */
    @NotEmpty(message = "文件类型不合法")
    @Schema(description = "文件类型")
    private String contentType;
    private String fileMd5;

}
