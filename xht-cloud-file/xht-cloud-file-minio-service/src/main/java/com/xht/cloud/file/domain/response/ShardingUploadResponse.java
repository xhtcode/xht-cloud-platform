package com.xht.cloud.file.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.cloud.framework.domain.response.Response;
import lombok.Data;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShardingUploadResponse extends Response {

    private String uploadId;

    private String fileName;

    private Boolean merge;

    /**
     * 分片索引
     */
    private Integer shardingIndex;
}
