package com.xht.cloud.generate.module.filedisk.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "GenFileDiskCreateRequest", description = "文件管理")
public class GenFileDiskCreateRequest extends CreateRequest {

    /**
     * 上级目录
     */
    @Schema(description = "上级目录")
    private String parentId;

    /**
     * 配置id
     */
    @Schema(description = "配置id")
    private Long configId;

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String fileName;

    /**
     * 文件描述
     */
    @Schema(description = "文件描述")
    private String fileDesc;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String filePath;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    private String fileContent;

    /**
     * 文件排序
     */
    @Schema(description = "文件排序")
    private Integer fileSort;

    /**
     * 忽略字段
     */
    @Schema(description = "忽略字段")
    private String ignoreField;
    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String fileSuffix;

    /**
     * 代码路径
     */
    @Schema(description = "文件后缀")
    private String fileCodePath;
}
