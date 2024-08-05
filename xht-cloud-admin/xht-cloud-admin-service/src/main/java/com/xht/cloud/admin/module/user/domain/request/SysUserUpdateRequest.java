package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.UpdateRequest;
import com.xht.cloud.framework.web.validation.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：用户表-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserUpdateRequest(用户表-修改请求信息)")
public class SysUserUpdateRequest extends UpdateRequest<String> {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID `id` 校验不通过", groups = {Update.class})
    private String id;
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

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
