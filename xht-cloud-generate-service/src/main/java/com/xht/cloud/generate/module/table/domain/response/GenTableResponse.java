package com.xht.cloud.generate.module.table.domain.response;

import com.xht.cloud.framework.domain.response.Response;
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
    private Long id;

    /**
     * config_id
     */
    @Schema(description = "config_id")
    private Long configId;

    /**
     * 数据源id
     */
    @Schema(description = "数据源id")
    private Long genDbId;

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
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 业务名称
     */
    @Schema(description = "业务名称")
    private String serviceName;

    /**
     * 业务描述
     */
    @Schema(description = "业务描述")
    private String serviceDesc;

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
