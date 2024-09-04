package com.xht.cloud.admin.module.permissions.domain.response;

import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：系统角色菜单关联
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysRoleMenuResponse(系统角色菜单关联-响应信息)")
public class SysRoleMenuResponse extends Response {

    /**
     *
     */
    @Schema(description = "")
    private String roleId;

    /**
     *
     */
    @Schema(description = "")
    private String menuId;

}
