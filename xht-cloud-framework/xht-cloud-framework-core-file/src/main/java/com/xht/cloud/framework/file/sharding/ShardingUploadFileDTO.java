package com.xht.cloud.framework.file.sharding;

import com.xht.cloud.framework.core.convert.ConsumerConvert;
import lombok.*;

import java.io.InputStream;
import java.util.Map;

/**
 * 描述 ：分片上传初始化信息
 *
 * @author 小糊涂
 **/
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShardingUploadFileDTO {

    /**
     * 桶名称
     */
    @Getter
    private final String bucketName;

    /**
     * 区域(可选)
     */
    @Getter
    private final String region;

    /**
     * 文件上传名称
     */
    @Getter
    private final String objectName;

    /**
     * 分片文件输入流
     */
    @Getter
    private final InputStream data;

    /**
     * 分片文件大小
     */
    @Getter
    private final long length;

    /**
     * 分片文件上传id
     */
    @Getter
    private final String uploadId;

    /**
     * 分片文件索引
     */
    @Getter
    private final int partIndex;

    /**
     * 额外的请求头(可选)
     */
    @Singular
    private final Map<String, String> extraHeaders;

    /**
     * 额外的查询参数(可选)
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
