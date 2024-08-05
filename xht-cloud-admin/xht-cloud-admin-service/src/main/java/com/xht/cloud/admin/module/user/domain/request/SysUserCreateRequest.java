package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：用户表-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserCreateRequest(用户表-增加请求信息)")
public class SysUserCreateRequest extends CreateRequest {
    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    @NotBlank(message = "用户名称 `userName` 校验不通过")
    private String userName;


    /**
     * 部门id
     */
    @Schema(description = "部门id")
    @NotBlank(message = "部门id校验不通过")
    private String deptId;
}
