package com.xht.cloud.framework.file.sharding;

import com.xht.cloud.framework.core.convert.ConsumerConvert;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：分片上传初始化信息
 *
 * @author 小糊涂
 **/
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShardingUploadInitDTO implements Serializable {

    /**
     * 桶名称
     */
    @Getter
    private final String bucketName;

    /**
     * 区域
     */
    @Getter
    private final String region;

    /**
     * 文件信息
     */
    @Getter
    private final String objectName;

    /**
     * 请求头信息
     */
    @Singular
    private final Map<String, String> extraHeaders;

    /**
     * 额外查询参数
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
