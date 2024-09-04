package com.xht.cloud.admin.module.config.domain.response;

import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：系统配置信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysConfigResponse(系统配置信息-响应信息)")
public class SysConfigResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 配置编码
     */
    @Schema(description = "配置编码")
    private String configCode;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    private String configName;

    /**
     * 配置信息(存放json)
     */
    @Schema(description = "配置信息(存放json)")
    private String configInfo;

    /**
     * 转换的类名称(默认转换成map)
     */
    @Schema(description = "转换的类名称(默认转换成map)")
    private String className;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述")
    private String description;

    /**
     * 状态（1正常0停用）
     */
    @Schema(description = "状态（1正常0停用）")
    private Integer status;

}
