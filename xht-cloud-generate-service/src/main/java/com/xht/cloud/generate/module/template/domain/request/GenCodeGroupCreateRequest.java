package com.xht.cloud.generate.module.template.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeGroupRequest(-增加请求信息)", description = "-增加请求信息")
public class GenCodeGroupCreateRequest extends CreateRequest {

    /**
     * 组名称
     */
    @Schema(description = "组名称")
    private String groupName;

    /**
     * 组描述
     */
    @Schema(description = "组描述")
    private String groupDesc;
}
