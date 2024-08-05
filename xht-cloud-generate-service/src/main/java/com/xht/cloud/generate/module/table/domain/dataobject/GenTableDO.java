package com.xht.cloud.generate.module.table.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseNoneDeleteDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "gen_table")
public class GenTableDO extends BaseNoneDeleteDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * id
     */
    @TableField(value = "config_id")
    private String configId;

    /**
     * 数据源id
     */
    @TableField(value = "gen_db_id")
    private String genDbId;

    /**
     * 表所在的数据库名称
     */
    @TableField(value = "table_schema")
    private String tableSchema;

    /**
     * 数据库引擎
     */
    @TableField(value = "table_engine")
    private String tableEngine;

    /**
     * 表名称
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 业务名称
     */
    @TableField(value = "module_label")
    private String moduleLabel;

    /**
     * 业务描述
     */
    @TableField(value = "module_value")
    private String moduleValue;

    /**
     * 业务模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;
    /**
     * 业务模块描述
     */
    @TableField(value = "module_desc")
    private String moduleDesc;

    /**
     * 权限前缀
     */
    @TableField(value = "authorization_prefix")
    private String authorizationPrefix;
    /**
     * controller地址前缀
     */
    @TableField(value = "path_url")
    private String pathUrl;

    /**
     * 代码名称
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 生成类型
     */
    @TableField(value = "gen_type")
    private String genType;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private String menuId;

    /**
     * 是否支持 1支持
     */
    @TableField(value = "lombok")
    private String lombok;

    /**
     * 是否支持 1支持
     */
    @TableField(value = "swagger")
    private String swagger;

    /**
     * 是否支持 1支持
     */
    @TableField(value = "jsr303")
    private String jsr303;

    /**
     * 是否支持 1支持
     */
    @TableField(value = "authorization")
    private String authorization;

    /**
     * 生成的方式 0单表 1树表
     */
    @TableField(value = "template_type")
    private String templateType;

    /**
     * 树表的上级id
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 树表的名字
     */
    @TableField(value = "parent_name")
    private String parentName;


    /**
     * 表创建时间
     */
    @TableField(value = "table_create_time")
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    @TableField(value = "table_update_time")
    private LocalDateTime tableUpdateTime;

}
