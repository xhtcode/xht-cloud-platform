package com.xht.cloud.generate.module.table.domain.request;


import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述 ：代码生成器-数据库信息-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenTableRequest(代码生成器-数据库信息-修改请求信息)", description = "代码生成器-数据库信息-修改请求信息")
public class GenTableUpdateRequest implements IUpdateRequestFun<String> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 校验不通过")
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
    @NotBlank(message = "表所在的数据库名称 `tableSchema` 校验不通过")
    private String tableSchema;

    /**
     * 数据库引擎
     */
    @Schema(description = "数据库引擎")
    @NotBlank(message = "数据库引擎 `tableEngine` 校验不通过")
    private String tableEngine;

    /**
     * 表名称
     */
    @Schema(description = "表名称")
    @NotBlank(message = "表名称 `tableName` 校验不通过")
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
    @NotBlank(message = "权限前缀 `authorizationPrefix` 校验不通过")
    @Schema(description = "权限前缀")
    private String authorizationPrefix;
    /**
     * controller地址前缀
     */
    @Schema(description = "controller地址前缀")
    @NotBlank(message = "controller地址前缀 `pathUrl` 校验不通过")
    private String pathUrl;

    /**
     * 代码名称
     */
    @Schema(description = "代码名称")
    @NotBlank(message = "代码名称 `codeName` 校验不通过")
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

    private List<GenTableColumnUpdateRequest> columns;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
