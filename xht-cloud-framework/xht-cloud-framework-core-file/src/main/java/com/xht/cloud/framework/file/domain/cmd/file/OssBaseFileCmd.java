package com.xht.cloud.framework.file.domain.cmd.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.file.domain.cmd.AbstractBaseOssCmd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：oss 文件
 *
 * @author 小糊涂
 **/
@Data
public class OssBaseFileCmd extends AbstractBaseOssCmd {

    /**
     * 头部参数
     */
    @Schema(description = "头部参数")
    protected Map<String, String> headers = new HashMap<>();

    /**
     * 用户元数据
     */
    @Schema(description = " 用户元数据")
    protected Map<String, String> userMetadata = new HashMap<>();

    @JsonIgnore
    public final OssBaseFileCmd putHeaders(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    @JsonIgnore
    public final OssBaseFileCmd putUserMetadata(String key, String value) {
        this.userMetadata.put(key, value);
        return this;
    }

    public final Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public final Map<String, String> getHeaders() {
        return headers;
    }

}
