package com.xht.cloud.generate.module.table.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenTableResponse(代码生成器-数据库信息-响应信息)", description = "代码生成器-数据库信息")
public class GenTableResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * config_id
     */
    @Schema(description = "config_id")
    private String configId;

    /**
     * 数据源id
     */
    @Schema(description = "数据源id")
    private String genDbId;

    /**
     * 表所在的数据库名称
     */
    @Schema(description = "表所在的数据库名称")
    private String tableSchema;

    /**
     * 数据库引擎
     */
    @Schema(description = "数据库引擎")
    private String tableEngine;

    /**
     * 表名称
     */
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 业务名称
     */
    @Schema(description = "业务名称")
    private String moduleLabel;

    /**
     * 业务描述
     */
    @Schema(description = "业务描述")
    private String moduleValue;
    /**
     * 业务模块名称
     */
    @Schema(description = "业务模块名称")
    private String moduleName;

    /**
     * 业务模块描述
     */
    @Schema(description = "业务模块描述")
    private String moduleDesc;
    /**
     * 权限前缀
     */
    @Schema(description = "权限前缀")
    private String authorizationPrefix;
    /**
     * controller地址前缀
     */
    @Schema(description = "controller地址前缀")
    private String pathUrl;

    /**
     * 代码名称
     */
    @Schema(description = "代码名称")
    private String codeName;

    /**
     * 生成类型
     */
    @Schema(description = "生成类型")
    private String genType;

    /**
     * 菜单id
     */
    @Schema(description = "菜单id")
    private String menuId;


    /**
     * 是否支持 1支持
     */
    @Schema(description = "lombok")
    private String lombok;

    /**
     * 是否支持 1支持
     */
    @Schema(description = "swagger")
    private String swagger;

    /**
     * 是否支持 1支持
     */
    @Schema(description = "jsr303")
    private String jsr303;

    /**
     * 是否支持权限 1支持
     */
    @Schema(description = "权限")
    private String authorization;

    /**
     * 生成的方式 0单表 1树表
     */
    @Schema(description = "生成的方式")
    private String templateType;

    /**
     * 树表的上级id
     */
    @Schema(description = "树表的上级id")
    private String parentId;

    /**
     * 树表的名字
     */
    @Schema(description = "树表的名字")
    private String parentName;

    /**
     * 表创建时间
     */
    @Schema(description = "表创建时间")
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    @Schema(description = "表更新时间")
    private LocalDateTime tableUpdateTime;

}
