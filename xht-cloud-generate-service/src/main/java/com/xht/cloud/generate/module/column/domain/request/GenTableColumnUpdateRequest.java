package com.xht.cloud.generate.module.column.domain.request;


import com.xht.cloud.framework.domain.request.UpdateRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 描述 ：代码生成业务字段-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenTableColumnRequest(代码生成业务字段-修改请求信息)", description = "代码生成业务字段-修改请求信息")
public class GenTableColumnUpdateRequest extends UpdateRequest<Long> {

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    @NotNull(message = "字段名字 `id` 校验不通过")
    private Long id;
    /**
     * 表id
     */
    @Schema(description = "表id")
    @NotNull(message = "表id `tableId` 校验不通过")
    private Long tableId;

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    @NotBlank(message = "字段名字 `columnName` 校验不通过")
    private String columnName;

    /**
     * 字段长度
     */
    @Schema(description = "字段长度")
    @IntegerInterval(message = "字段长度 `columnLength` 字段值在0到999之间")
    private Integer columnLength;

    /**
     * 字段名字
     */
    @Schema(description = "字段名字")
    @NotBlank(message = "字段名字 `columnCodeName` 校验不通过")
    private String columnCodeName;

    /**
     * 字段描述
     */
    @Schema(description = "字段描述")
    @NotBlank(message = "字段描述 `columnComment` 校验不通过")
    private String columnComment;

    /**
     * 数据库字段类型
     */
    @Schema(description = "数据库字段类型")
    @NotBlank(message = "数据库字段类型 `columnDbType` 校验不通过")
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

    /**
     * 获取主键
     */
    @Override
    public Long getPkId() {
        return this.id;
    }
}
