package com.xht.cloud.admin.module.dict.domain.request;

import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：字典-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictRequest(字典-修改请求信息)")
public class SysDictTypeUpdateRequest extends SysDictTypeCreateRequest implements IUpdateRequestFun<String> {

    /**
     * Id
     */
    @Schema(description = "Id")
    @NotBlank(message = "Id `id` 不能为空")
    private String id;


    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
