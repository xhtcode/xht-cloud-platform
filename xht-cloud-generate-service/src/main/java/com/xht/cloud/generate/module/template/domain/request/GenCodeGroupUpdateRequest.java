package com.xht.cloud.generate.module.template.domain.request;

import com.xht.cloud.framework.core.domain.request.UpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeGroupRequest(-修改请求信息)", description = "-修改请求信息")
public class GenCodeGroupUpdateRequest extends UpdateRequest<String> {

    /**
     * 
     */
    @Schema(description = "")
    @NotBlank(message = " `id` 校验不通过")
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

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
