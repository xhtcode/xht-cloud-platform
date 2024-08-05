package com.xht.cloud.framework.file.sharding;

import com.xht.cloud.framework.core.convert.ConsumerConvert;
import lombok.*;

import java.util.Map;

/**
 * 描述 ：分片合并
 *
 * @author 小糊涂
 **/
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShardingUploadMergeDTO<T> {

    /**
     * 存储桶
     */
    @Getter
    private final String bucketName;

    /**
     * 区域(可选)
     */
    @Getter
    private final String region;

    /**
     * 对象名
     */
    @Getter
    private final String objectName;

    /**
     * 上传ID
     */
    @Getter
    private final String uploadId;

    /**
     * 分片
     */
    @Getter
    private final T[] parts;

    /**
     * 请求头信息(可选)
     */
    @Singular
    private final Map<String, String> extraHeaders;

    /**
     * 额外查询参数(可选)
     */
    @Singular
    private final Map<String, String> extraQueryParams;

    /**
     * 请求头转换
     *
     * @param consumerConvert {@link ConsumerConvert}
     */
    public <T> T extraHeadersConvert(ConsumerConvert<Map<String, String>, T> consumerConvert) {
        return consumerConvert.convert(this.extraHeaders);
    }

    /**
     * 额外的请求参数
     *
     * @param consumerConvert {@link ConsumerConvert}
     */
    public <T> T extraQueryParamsConvert(ConsumerConvert<Map<String, String>, T> consumerConvert) {
        return consumerConvert.convert(this.extraQueryParams);
    }
}
