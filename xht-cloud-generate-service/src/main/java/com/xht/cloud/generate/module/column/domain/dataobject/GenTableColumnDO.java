package com.xht.cloud.generate.module.column.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseNoneDeleteDO;
import lombok.Data;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "gen_table_column")
public class GenTableColumnDO extends BaseNoneDeleteDO {

    /**
     * 字段名字
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 表id
     */
    @TableField(value = "table_id")
    private Long tableId;

    /**
     * 字段名字
     */
    @TableField(value = "column_name")
    private String columnName;

    /**
     * 字段长度
     */
    @TableField(value = "column_length")
    private Integer columnLength;

    /**
     * 字段名字
     */
    @TableField(value = "column_code_name")
    private String columnCodeName;

    /**
     * 字段描述
     */
    @TableField(value = "column_comment")
    private String columnComment;

    /**
     * 数据库字段类型
     */
    @TableField(value = "column_db_type")
    private String columnDbType;


    /**
     * 是否主键（1是）
     */
    @TableField(value = "column_pk")
    private String columnPk;

    /**
     * 是否列表（1是）
     */
    @TableField(value = "column_list")
    private String columnList;

    /**
     * 增加、修改数据（1是）
     */
    @TableField(value = "column_operation")
    private String columnOperation;

    /**
     * 是否查询（1是）
     */
    @TableField(value = "column_query")
    private String columnQuery;

    /**
     * 是否必填（1是）
     */
    @TableField(value = "column_required")
    private String columnRequired;

    /**
     * 字段排序
     */
    @TableField(value = "column_sort")
    private Integer columnSort;

    /**
     * 字段名称 genFileId
     */
    @TableField(exist = false)
    private String columnNameGet;

    /**
     * 字段名称 set
     */
    @TableField(exist = false)
    private String columnNameSet;

    @TableField(exist = false)
    private String tsName;

    @TableField(exist = false)
    private String javaName;

}
