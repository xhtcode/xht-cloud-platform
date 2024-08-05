package com.xht.cloud.generate.module.template.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeGroupResponse(-响应信息)", description = "")
public class GenCodeGroupResponse extends Response {

    /**
     * 
     */
    @Schema(description = "")
    private String id;

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
