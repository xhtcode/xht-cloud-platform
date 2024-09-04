package com.xht.cloud.generate.module.column.domain.response;


import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenTableColumnResponse(代码生成业务字段-响应信息)", description = "代码生成业务字段")
public class GenTableColumnResponse extends Response {

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    private Long id;

    /**
     * 表id
     */
    @Schema(description = "表id")
    private Long tableId;

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    private String columnName;

    /**
     * 字段长度
     */
    @Schema(description = "字段长度")
    private Integer columnLength;

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    private String columnCodeName;

    /**
     * 字段描述
     */
    @Schema(description = "字段描述")
    private String columnComment;

    /**
     * 数据库字段类型
     */
    @Schema(description = "数据库字段类型")
    private String columnDbType;

    /**
     * 是否主键（1是）
     */
    @Schema(description = "是否主键（1是）")
    private String columnPk;

    /**
     * 是否列表（1是）
     */
    @Schema(description = "是否列表（1是）")
    private String columnList;

    /**
     * 增加、修改数据（1是）
     */
    @Schema(description = "增加、修改数据（1是）")
    private String columnOperation;

    /**
     * 是否查询（1是）
     */
    @Schema(description = "是否查询（1是）")
    private String columnQuery;

    /**
     * 是否必填（1是）
     */
    @Schema(description = "是否必填（1是）")
    private String columnRequired;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    private Integer columnSort;

}
