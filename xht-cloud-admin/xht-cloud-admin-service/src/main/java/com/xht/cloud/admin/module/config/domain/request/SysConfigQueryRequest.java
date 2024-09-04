package com.xht.cloud.admin.module.config.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：系统配置信息-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysConfigRequest(系统配置信息-查询请求信息)")
public class SysConfigQueryRequest extends PageQueryRequest {

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
     * 状态（1正常0停用）
     */
    @Schema(description = "状态（1正常0停用）")
    private Integer status;
}
