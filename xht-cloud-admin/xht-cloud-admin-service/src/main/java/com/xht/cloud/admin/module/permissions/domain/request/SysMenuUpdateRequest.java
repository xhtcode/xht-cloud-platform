package com.xht.cloud.admin.module.permissions.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：菜单权限表-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysMenuUpdateRequest(菜单权限表-修改请求信息)")
public class SysMenuUpdateRequest extends SysMenuCreateRequest {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 不能为空")
    private String id;

}
