package com.xht.cloud.admin.module.log.domain.request;

import com.xht.cloud.admin.api.log.enums.LoginStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysLoginLogQueryRequest(系统登录日志记录-查询请求信息)")
public class SysLoginLogQueryRequest extends PageQueryRequest {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

    /**
     * 登录状态
     */
    @Schema(description = "登录状态")
    private LoginStatusEnums loginStatus;

}
