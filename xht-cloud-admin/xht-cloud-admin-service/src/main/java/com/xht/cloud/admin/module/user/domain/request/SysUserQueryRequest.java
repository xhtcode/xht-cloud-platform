package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：用户表-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysUserQueryRequest(用户表-查询请求信息)")
public class SysUserQueryRequest extends PageQueryRequest {

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    @NotBlank(message = "部门id校验不通过")
    private String deptId;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String nickName;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private List<String> userIds;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
     private UserStatusEnums userStatus;

}
