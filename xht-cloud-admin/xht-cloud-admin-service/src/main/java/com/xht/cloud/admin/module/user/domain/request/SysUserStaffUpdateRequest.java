package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.IUpdateRequestFun;
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
@Schema(name = "SysUserStaffUpdateRequest(用户表-修改请求信息)")
public class SysUserStaffUpdateRequest extends SysUserStaffCreateRequest implements IUpdateRequestFun<String> {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID `id` 校验不通过")
    private String id;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
