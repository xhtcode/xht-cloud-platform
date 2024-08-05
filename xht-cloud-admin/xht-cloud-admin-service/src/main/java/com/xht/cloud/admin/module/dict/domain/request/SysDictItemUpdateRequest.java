package com.xht.cloud.admin.module.dict.domain.request;

import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：字典数据-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictItemRequest(字典数据-修改请求信息)")
public class SysDictItemUpdateRequest extends SysDictItemDO implements IUpdateRequestFun<String> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 不能为空")
    private String id;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
