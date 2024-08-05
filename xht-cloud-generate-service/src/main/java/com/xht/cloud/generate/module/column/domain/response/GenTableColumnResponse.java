package com.xht.cloud.generate.module.column.domain.response;


import com.xht.cloud.framework.core.domain.response.Response;
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
    private String id;

    /**
     * 表id
     */
    @Schema(description = "表id")
    private String tableId;

    /**
     * 表所在的数据库名称
     */
    @Schema(description = "表所在的数据库名称")
    private String tableSchema;

    /**
     * 表名称
     */
    @Schema(description = "表名称")
    private String tableName;

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
     * Java字段类型
     */
    @Schema(description = "Java字段类型")
    private String columnJavaType;

    /**
     * Ts字段名称
     */
    @Schema(description = "Ts字段名称")
    private String columnTsType;

    /**
     * 字段值示例
     */
    @Schema(description = "字段值示例")
    private String columnExample;

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
     * 是否增加（1是）
     */
    @Schema(description = "是否增加（1是）")
    private String columnSave;

    /**
     * 是否修改（1是）
     */
    @Schema(description = "是否修改（1是）")
    private String columnUpdate;

    /**
     * 是否查询（1是）
     */
    @Schema(description = "是否查询（1是）")
    private String columnQuery;

    /**
     * 是否必填（1是）
     */
    @Schema(description = "是否必填（1是）")
    private String columnNullable;

    /**
     * 是否字典（1是）
     */
    @Schema(description = "是否字典（1是）")
    private String columnDict;

    /**
     * 字段显示
     */
    @Schema(description = "字段显示")
    private String columnHtml;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    private Integer columnSort;

    /**
     * 查询方式
     */
    @Schema(description = "查询方式")
    private String columnQueryType;
}
