package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysAdminUpdateRequest(管理员-增加请求信息)")
public class SysAdminUpdateRequest extends SysAdminCreateRequest implements IUpdateRequestFun<Integer> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull(message = "id `id` 不能为空")
    private Integer id;

    /**
     * 获取主键
     */
    @Override
    public Integer getPkId() {
        return this.id;
    }
}
