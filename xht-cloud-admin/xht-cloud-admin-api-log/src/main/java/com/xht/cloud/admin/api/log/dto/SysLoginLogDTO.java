package com.xht.cloud.admin.api.log.dto;

import com.xht.cloud.admin.api.log.enums.LoginStatusEnums;
import com.xht.cloud.framework.domain.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：系统登录日志
 *
 * @author : 小糊涂
 **/
@Data
@Tag(name = "SysLoginLogDTO", description = "系统登录日志")
public class SysLoginLogDTO extends DTO {

    /**
     * 日志主键
     */
    @Schema(description = "日志主键")
    private Long id;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private Integer userType;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String nickName;

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
