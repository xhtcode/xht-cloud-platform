package com.xht.cloud.admin.module.log.domain.response;

import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.core.domain.response.Response;
import com.xht.cloud.admin.api.log.enums.LoginStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysLoginLogResponse(系统登录日志记录-响应信息)")
public class SysLoginLogResponse extends Response {

    /**
     * 日志主键
     */
    @Schema(description = "日志主键")
    private Long id;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private String loginType;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 登录的IP地址
     */
    @Schema(description = "登录的IP地址")
    private String loginIp;

    /**
     * 登录的归属地
     */
    @Schema(description = "登录的归属地")
    private String loginAddress;

    /**
     * 登录的浏览器
     */
    @Schema(description = "登录的浏览器")
    private String userAgent;

    /**
     * 登录状态 0登录成功 1登录失败
     */
    @Schema(description = "登录状态")
    private LoginStatusEnums loginStatus;

    /**
     * 登录描述
     */
    @Schema(description = "登录描述")
    private String loginDesc;

}
