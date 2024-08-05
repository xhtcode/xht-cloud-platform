package com.xht.cloud.framework.minio;

import com.xht.cloud.framework.file.core.OssProperties;
import com.xht.cloud.framework.minio.client.CustomMinioClient;
import com.xht.cloud.framework.minio.repository.MinioFileRepository;
import com.xht.cloud.framework.minio.service.MinioOssTemplate;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * 描述 ：minio自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({OssProperties.class})
public class MinioAutoConfiguration {

    private final OssProperties minioProperties;

    public MinioAutoConfiguration(OssProperties minioProperties) {
        this.minioProperties = minioProperties;
        log.debug(">>>>>>minio-start minio自动配置<<<<<<");
    }

    @Bean
    public MinioClient minioClient() {
        // @formatter:off
        return MinioClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        // @formatter:on
    }

    /**
     * 配置用于分片上传的Minio连接客户端
     */
    @Bean
    public CustomMinioClient customMinioClient() {
        MinioAsyncClient asyncClient = MinioAsyncClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return new CustomMinioClient(asyncClient);
    }

    @Bean
    @ConditionalOnBean(MinioClient.class)
    public MinioFileRepository getMinioFileRepository() {
        return new MinioFileRepository(minioClient());
    }

    @Bean
    @ConditionalOnBean(MinioClient.class)
    public MinioOssTemplate getMinioTemplate() {
        return new MinioOssTemplate();
    }

}
