package com.xht.cloud.admin.module.sequence.domain.request;

import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import com.xht.cloud.framework.web.validation.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysSequenceUpdateRequest(序列生成器-公共修改信息)")
public class SysSequenceUpdateRequest extends SysSequenceCreateRequest implements IUpdateRequestFun<String> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotEmpty(message = "id", groups = {Update.class})
    private String id;

    @Override
    public String getPkId() {
        return this.id;
    }

}
