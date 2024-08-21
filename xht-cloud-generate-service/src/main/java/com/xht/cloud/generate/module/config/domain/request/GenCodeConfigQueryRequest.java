package com.xht.cloud.generate.module.config.domain.request;

import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import com.xht.cloud.framework.web.validation.group.Create;
import com.xht.cloud.framework.web.validation.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述 ：代码生成器-配置中心-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeConfigRequest(代码生成器-配置中心-查询请求信息)", description = "代码生成器-配置中心-查询请求信息")
public class GenCodeConfigQueryRequest extends PageQueryRequest {
    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    @NotEmpty(message = "配置名称", groups = {Update.class, Create.class})
    private String configName;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述")
    @NotEmpty(message = "配置描述", groups = {Update.class, Create.class})
    private String configDesc;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @IntegerInterval(message = "排序 `sort` 字段值在0到999之间")
    private Integer configSort;


}
