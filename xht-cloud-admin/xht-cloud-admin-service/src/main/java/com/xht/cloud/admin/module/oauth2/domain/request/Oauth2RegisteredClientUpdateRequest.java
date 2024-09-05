package com.xht.cloud.admin.module.oauth2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：oauth2 客户端信息-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "Oauth2RegisteredClientRequest(oauth2 客户端信息-修改请求信息)")
public class Oauth2RegisteredClientUpdateRequest extends Oauth2RegisteredClientCreateRequest {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;


}
