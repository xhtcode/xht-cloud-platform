package com.xht.cloud.admin.module.dept.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：部门-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDeptRequest(部门-修改请求信息)")
public class SysDeptUpdateRequest extends SysDeptCreateRequest  {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 校验不通过")
    private String id;

}
