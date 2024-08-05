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
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 表id
     */
    @TableField(value = "table_id")
    private String tableId;

    /**
     * 表所在的数据库名称
     */
    @TableField(value = "table_schema")
    private String tableSchema;

    /**
     * 表名称
     */
    @TableField(value = "table_name")
    private String tableName;

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
     * Java字段类型
     */
    @TableField(value = "column_java_type")
    private String columnJavaType;

    /**
     * Ts字段名称
     */
    @TableField(value = "column_ts_type")
    private String columnTsType;

    /**
     * 字段值示例
     */
    @TableField(value = "column_example")
    private String columnExample;

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
     * 是否增加（1是）
     */
    @TableField(value = "column_save")
    private String columnSave;

    /**
     * 是否修改（1是）
     */
    @TableField(value = "column_update")
    private String columnUpdate;

    /**
     * 是否查询（1是）
     */
    @TableField(value = "column_query")
    private String columnQuery;

    /**
     * 是否必填（1是）
     */
    @TableField(value = "column_nullable")
    private String columnNullable;

    /**
     * 是否字典（1是）
     */
    @TableField(value = "column_dict")
    private String columnDict;

    /**
     * 字段显示
     */
    @TableField(value = "column_html")
    private String columnHtml;

    /**
     * 字段排序
     */
    @TableField(value = "column_sort")
    private Integer columnSort;

    /**
     * 查询方式
     */
    @TableField(value = "column_query_type")
    private String columnQueryType;


    /**
     * 字段名称 get
     */
    @TableField(exist = false)
    private String columnNameGet;

    /**
     * 字段名称 set
     */
    @TableField(exist = false)
    private String columnNameSet;

    /**
     * 字段名称 set
     */
    @TableField(exist = false)
    private String convertMethod;

}
