package com.xht.cloud.admin.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ： 管理员用户信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysAdminUserResDTO", description = "管理员用户信息")
public class SysAdminUserResDTO {

    /**
     * id
     */
    @Schema(description = "id")
    private Integer id;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 账号密码
     */
    @Schema(description = "账号密码")
    private String passWord;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String userAvatar;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String contactPhone;
}
