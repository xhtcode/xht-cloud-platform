package com.xht.cloud.framework.file.domain.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：公共的
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
@Schema(description = "公共的参数")
public abstract class AbstractBaseOssCmd {

    /**
     * 额外的头部参数
     */
    @Schema(description = "额外的头部参数")
    protected Map<String, String> extraHeaders = new HashMap<>();

    /**
     * 额外的查询的参数
     */
    @Schema(description = "额外的查询的参数")
    protected Map<String, String> extraQueryParams = new HashMap<>();


    @JsonIgnore
    public final AbstractBaseOssCmd putExtraHeaders(String key, String value) {
        this.extraHeaders.put(key, value);
        return this;
    }

    @JsonIgnore
    public final AbstractBaseOssCmd putExtraQueryParams(String key, String value) {
        this.extraQueryParams.put(key, value);
        return this;
    }


    public final Map<String, String> getExtraHeaders() {
        return extraHeaders;
    }

    public final Map<String, String> getExtraQueryParams() {
        return extraQueryParams;
    }
}
