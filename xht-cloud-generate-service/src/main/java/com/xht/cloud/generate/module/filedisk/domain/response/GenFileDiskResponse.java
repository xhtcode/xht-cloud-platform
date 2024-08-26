package com.xht.cloud.generate.module.filedisk.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "GenFileDiskResponse", description = "文件管理")
public class GenFileDiskResponse extends Response {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "主键")
    private String id;

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
    @Schema(description = "代码路径")
    private String fileCodePath;
}
