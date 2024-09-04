package com.xht.cloud.admin.module.oauth2.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：oauth2 客户端信息-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "Oauth2RegisteredClientRequest(oauth2 客户端信息-查询请求信息)")
public class Oauth2RegisteredClientQueryRequest extends PageQueryRequest {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private String autoApprove;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    private String clientName;

    /**
     * 授权模式
     */
    @Schema(description = "授权模式")
    private String authorizationGrantTypes;

    /**
     * 允许客户端请求的范围
     */
    @Schema(description = "允许客户端请求的范围")
    private String scopes;

}
