package com.xht.cloud.file.domain.request;

import com.xht.cloud.framework.core.domain.request.Request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述 ：文件分片上传信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "ShardingUploadFileRequest(文件分片上传信息)")
public class ShardingUploadFileRequest extends Request {

    /**
     * 文件上传的名称
     */
    @NotEmpty(message = "文件上传的名称不合法")
    @Schema(description = "文件上传的名称")
    private String originalFileName;

    /**
     * 上传id
     */
    @NotEmpty(message = "上传id名称不合法")
    @Schema(description = "上传id")
    private String uploadId;


    /**
     * 文件名称
     */
    @NotEmpty(message = "上传id名称不合法")
    @Schema(description = "上传id")
    private String fileName;

    /**
     * 分块MD5
     */
    @NotEmpty(message = "分块MD5不合法")
    @Schema(description = "分块MD5")
    private String shardingMD5;

    /**
     * 分片索引
     */
    @NotNull(message = "分片索引不合法")
    @Schema(description = "分片索引")
    private int shardingIndex;

    /**
     * 分片长度
     */
    @NotNull(message = "分片长度不合法")
    @Schema(description = "分片长度")
    private int shardingLength;

    /**
     * 文件总大小
     */
    @NotNull(message = "文件总大小不合法")
    @Schema(description = "文件总大小")
    private long fileSizeCount;

    /**
     * 分片数据块
     */
    @NotNull(message = "分片数据块不合法")
    @Schema(description = "分片数据块")
    private MultipartFile file;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    @NotEmpty(message = "文件类型不合法")
    private String contentType;

}
