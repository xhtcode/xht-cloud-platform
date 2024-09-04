package com.xht.cloud.file.domain.response;

import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.framework.domain.response.Response;
import com.xht.cloud.framework.file.convert.FileConvert;
import com.xht.cloud.framework.file.utils.FileSizeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：文件元数据信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysMetadataFileResponse(文件元数据信息-响应信息)")
public class SysMetadataFileResponse extends Response {
    /**
     * 主键ID，自增长
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 存储桶名称
     */
    @Schema(description = "存储桶名称")
    private String bucket;

    /**
     * 存储桶路径
     */
    @Schema(description = "存储桶路径")
    private String bucketPath;

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 上传文件名
     */
    @Schema(description = "上传文件名")
    private String fileOriginalName;

    /**
     * MIME类型
     */
    @Schema(description = "MIME类型")
    private String fileContentType;

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String fileSuffix;

    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    private long fileSize;

    /**
     * 文件状态
     */
    @Schema(description = "文件状态")
    private FileStatusEnums fileStatus;

    /**
     * 获取文件全名称
     */
    public String getFullFileName() {
        return FileConvert.getFileName(this.fileName, this.fileSuffix);
    }

    /**
     * 获取格式化后的文件大小
     */
    public String getSize() {
        return FileSizeFormat.formatFileSize(this.fileSize);
    }

}
