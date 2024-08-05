package com.xht.cloud.admin.module.config.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：系统配置信息-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysConfigRequest(系统配置信息-增加请求信息)")
public class SysConfigCreateRequest extends CreateRequest {
    /**
     * 配置编码
     */
    @Schema(description = "配置编码")
    @NotBlank(message = "配置编码 `configCode` 校验不通过")
    private String configCode;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    @NotBlank(message = "配置名称 `configName` 校验不通过")
    private String configName;

    /**
     * 配置信息(存放json)
     */
    @Schema(description = "配置信息(存放json)")
    @NotBlank(message = "配置信息(存放json) `configInfo` 校验不通过")
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
    @IntegerInterval(message = "状态（1正常0停用） `status` 字段值在0到999之间")
    private Integer status;
}
