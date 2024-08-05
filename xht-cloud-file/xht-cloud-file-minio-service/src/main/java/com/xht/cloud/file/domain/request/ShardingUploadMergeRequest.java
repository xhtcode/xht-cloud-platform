package com.xht.cloud.file.domain.request;

import com.xht.cloud.framework.core.domain.request.Request;
import lombok.Data;

/**
 * 描述 ：文件分片上传合并请求信息
 *
 * @author 小糊涂
 **/
@Data
public class ShardingUploadMergeRequest extends Request {
    /**
     * 对象名
     */
    private String objectName;

    /**
     * 上传ID
     */
    private String uploadId;
}
