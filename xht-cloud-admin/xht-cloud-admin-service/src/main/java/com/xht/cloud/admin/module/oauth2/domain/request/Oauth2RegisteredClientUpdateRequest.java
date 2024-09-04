package com.xht.cloud.admin.module.oauth2.domain.request;

import com.xht.cloud.framework.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：oauth2 客户端信息-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "Oauth2RegisteredClientRequest(oauth2 客户端信息-修改请求信息)")
public class Oauth2RegisteredClientUpdateRequest extends Oauth2RegisteredClientCreateRequest implements IUpdateRequestFun<String> {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;


    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
