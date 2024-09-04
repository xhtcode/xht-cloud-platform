package com.xht.cloud.admin.module.permissions.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：系统角色菜单关联表-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysRoleMenuRequest(系统角色菜单关联表-查询请求信息)")
public class SysRoleMenuQueryRequest extends PageQueryRequest {
    /**
     *
     */
    @Schema(description = "")
    @NotBlank(message = " `roleId` 校验不通过")
    private String roleId;

    /**
     *
     */
    @Schema(description = "")
    @NotBlank(message = " `menuId` 校验不通过")
    private String menuId;
}
