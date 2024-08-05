package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysAdminQueryRequest(管理员-增加请求信息)")
public class SysAdminQueryRequest extends PageQueryRequest {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;


    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String contactPhone;
}
