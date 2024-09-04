package com.xht.cloud.generate.module.database.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成器-数据源管理-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenDatabaseRequest(代码生成器-数据源管理-查询请求信息)", description = "代码生成器-数据源管理-查询请求信息")
public class GenDatabaseQueryRequest extends PageQueryRequest {

    /**
     * 连接名称
     */
    @Schema(description = "连接名称")
    private String connName;

    /**
     * 数据库连接
     */
    @Schema(description = "数据库连接")
    private String dbUrl;

    /**
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    private String dbType;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称")
    private String dbName;

    /**
     * 数据库描述
     */
    @Schema(description = "数据库描述")
    private String dbDescribe;

    /**
     * host
     */
    @Schema(description = "host")
    private String host;

    /**
     * 端口
     */
    @Schema(description = "端口")
    private Integer port;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String passWord;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
}
