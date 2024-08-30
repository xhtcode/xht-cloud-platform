package com.xht.cloud.admin.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "SysUserAdminResponse(管理员-增加请求信息)")
public class SysUserAdminResponse extends Response {

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
     * 密码
     */
    @JsonIgnore
    @Schema(description = "pass_word", hidden = true)
    private transient String passWord;

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
