package com.xht.cloud.framework.minio.service;


import cn.hutool.core.map.MapUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.file.constant.OssRequestMethod;
import com.xht.cloud.framework.file.convert.BucketConvert;
import com.xht.cloud.framework.file.domain.cmd.bucket.BaseBucketCmd;
import com.xht.cloud.framework.file.domain.dto.BucketDTO;
import com.xht.cloud.framework.file.exception.OssException;
import com.xht.cloud.framework.file.oss.OssBucketOperations;
import com.xht.cloud.framework.file.oss.OssObjectOperations;
import com.xht.cloud.framework.file.oss.OssShardingOperations;
import com.xht.cloud.framework.file.sharding.ShardingUploadFileDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadInitDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadListDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadMergeDTO;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.minio.client.CustomMinioClient;
import com.xht.cloud.framework.minio.convert.MethodConvert;
import com.xht.cloud.framework.minio.convert.MinioBucketConvertImpl;
import com.xht.cloud.framework.utils.support.StringUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Part;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ：minio 操作剧吐实现
 *
 * @author 小糊涂
 **/
@Slf4j
public class MinioOssTemplate implements OssBucketOperations, OssObjectOperations, OssShardingOperations<ListPartsResult, Part> {

    @Resource
    private MinioClient minioClient;

    @Resource
    private CustomMinioClient customMinioClient;


    private static final BucketConvert<Bucket> bucketConvert = new MinioBucketConvertImpl();
//---------------------------Minio Bucket 存储通基础操作服务 Start-------------------------------------

    /**
     * 查询所有存储桶
     *
     * @return Bucket 列表
     */
    @Override
    public List<BucketDTO> listBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return bucketConvert.convert(buckets);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketCmd 存储桶信息
     * @return 是否存在，true 存在，false 不存在
     */
    @SneakyThrows
    @Override
    public boolean bucketExists(BaseBucketCmd bucketCmd) {
        Assert.notNull(bucketCmd, "查询桶条件 不能为空!");
        BucketExistsArgs.Builder builder = BucketExistsArgs.builder();
        if (StringUtils.hasText(bucketCmd.getBucketName())) {
            builder.bucket(bucketCmd.getBucketName());
        }
        if (StringUtils.hasText(bucketCmd.getRegion())) {
            builder.region(bucketCmd.getBucketName());
        }
        if (!CollectionUtils.isEmpty(bucketCmd.getExtraHeaders())) {
            builder.extraHeaders(bucketCmd.getExtraHeaders());
        }
        if (!CollectionUtils.isEmpty(bucketCmd.getExtraQueryParams())) {
            builder.extraQueryParams(bucketCmd.getExtraQueryParams());
        }
        return minioClient.bucketExists(builder.build());
    }

    /**
     * 创建存储桶
     *
     * @param bucketCmd 存储桶信息
     */
    @SneakyThrows
    @Override
    public void makeBucket(BaseBucketCmd bucketCmd) {
        Assert.notNull(bucketCmd, "存储桶条件 不能为空!");
        if (bucketExists(bucketCmd)) {
            throw new OssException("存储桶已存在");
        }
        MakeBucketArgs.Builder builder = MakeBucketArgs.builder();
        if (StringUtils.hasText(bucketCmd.getBucketName())) {
            builder.bucket(bucketCmd.getBucketName());
        }
        if (StringUtils.hasText(bucketCmd.getRegion())) {
            builder.region(bucketCmd.getBucketName());
        }
        if (!CollectionUtils.isEmpty(bucketCmd.getExtraHeaders())) {
            builder.extraHeaders(bucketCmd.getExtraHeaders());
        }
        if (!CollectionUtils.isEmpty(bucketCmd.getExtraQueryParams())) {
            builder.extraQueryParams(bucketCmd.getExtraQueryParams());
        }
        minioClient.makeBucket(builder.build());
    }

    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    @SneakyThrows
    @Override
    public void removeBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            throw new OssException("存储桶不存在存在");
        }
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 上传文件
     *
     * @param uploadFileBO {@link UploadFileBO} 文件上传的参数
     */
    @Override
    public void putObject(UploadFileBO uploadFileBO) throws OssException {
        try {
            // @formatter:off
            PutObjectArgs.Builder builder = PutObjectArgs.builder();
            builder
                    .bucket(uploadFileBO.getBucket())
                    .object(uploadFileBO.getObjectName())
                    .contentType(uploadFileBO.getFileContentType())
                    .stream(uploadFileBO.getInputStream(), uploadFileBO.getSize(), uploadFileBO.getPartSize())
                    .headers(uploadFileBO.getHeaders())
                    .userMetadata(uploadFileBO.getUserMetadata())
                    .extraHeaders(uploadFileBO.getExtraHeaders())
                    .extraQueryParams(uploadFileBO.getExtraQueryParams());
            minioClient.putObject(builder.build());
            // @formatter:on
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param bucket      桶名称
     * @param object      文件名称
     * @param contentType 文件类型
     * @param stream      文件流
     * @param objectSize  文件流大小
     * @param partSize    分派大小 -1标识全部
     */
    @Override
    public void putObject(String bucket, String object, String contentType, InputStream stream, Long objectSize, Long partSize) throws OssException {
        try {
            // @formatter:off
            PutObjectArgs.Builder builder = PutObjectArgs.builder();
            builder
                    .bucket(bucket)
                    .object(object)
                    .contentType(contentType)
                    .stream(stream, objectSize, partSize);
            minioClient.putObject(builder.build());
            // @formatter:on
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 获取文件二进制流
     *
     * @param bucket bucket名称
     * @param object 文件名称
     * @return 二进制流
     */
    @Override
    public InputStream getObject(String bucket, String object) throws OssException {
        try {
            Assert.hasText(bucket, "bucket名称不能为空!");
            Assert.hasText(object, "文件名称不能为空！");
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(object).build());
        } catch (Exception e) {
            throw new OssException(e);
        }
    }


    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     */
    @Override
    public void delete(String bucketName, String fileName) throws OssException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 获取文件链接
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     * @param method     请求方式
     * @param duration   过期时间
     * @param timeUnit   过期时间类型
     * @return 分享的文件连接
     */
    public String getPreviewUrl(String bucketName, String fileName, OssRequestMethod method, Integer duration, TimeUnit timeUnit) throws Exception {
        Assert.hasText(bucketName, "bucket名称不能为空！");
        Assert.hasText(fileName, "文件名称不能为空！");
        Method convertMethod = MethodConvert.convert(method);
        Assert.notNull(convertMethod, "请求方式不能为空！");
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder();
        builder.bucket(bucketName)
                .object(fileName)
                .method(convertMethod);
        if (Objects.nonNull(duration) && Objects.nonNull(timeUnit)) {
            builder.expiry(duration, timeUnit);
        }
        return minioClient.getPresignedObjectUrl(builder.build());
    }

    /**
     * 文件分片上传初始化
     *
     * @param uploadInit {@link ShardingUploadInitDTO} 分片下载
     * @return {@link String} 上传id
     */
    @Override
    public String createMultipartUploadAsync(ShardingUploadInitDTO uploadInit) {
        // @formatter:off
        try {
            return customMinioClient.createMultipartUploadAsync(
                    uploadInit.getBucketName(),
                    uploadInit.getRegion(),
                    uploadInit.getObjectName(),
                    uploadInit.extraHeadersConvert(headers -> {
                        if (MapUtil.isEmpty(headers)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        headers.forEach(multimap::put);
                        return multimap;
                    }),
                    uploadInit.extraQueryParamsConvert(extraQueryParams -> {
                        if (MapUtil.isEmpty(extraQueryParams)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        extraQueryParams.forEach(multimap::put);
                        return multimap;
                    })
            ).get().result().uploadId();
        } catch (Exception e) {
            log.error("文件分片上传初始化: {}", e.getMessage(), e);
            throw new OssException("获取文件上传编号失败");
        }
        // @formatter:on
    }

    /**
     * 文件分片上传
     *
     * @param shardingUploadFileDTO 文件信息
     * @return {@link Boolean} true 成功
     */
    @Override
    public Boolean uploadPartAsync(ShardingUploadFileDTO shardingUploadFileDTO) {
        try {
            // @formatter:off
            customMinioClient.uploadPartAsync(
                    shardingUploadFileDTO.getBucketName(),
                    shardingUploadFileDTO.getRegion(),
                    shardingUploadFileDTO.getObjectName(),
                    shardingUploadFileDTO.getData(),
                    shardingUploadFileDTO.getLength(),
                    shardingUploadFileDTO.getUploadId(),
                    shardingUploadFileDTO.getPartIndex(),
                    shardingUploadFileDTO.extraHeadersConvert(headers -> {
                        if (MapUtil.isEmpty(headers)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        headers.forEach(multimap::put);
                        return multimap;
                    }),
                    shardingUploadFileDTO.extraQueryParamsConvert(extraQueryParams -> {
                        if (MapUtil.isEmpty(extraQueryParams)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        extraQueryParams.forEach(multimap::put);
                        return multimap;
                    })
            ).get();
            return Boolean.TRUE;
            // @formatter:on
        } catch (Exception e) {
            log.error("文件分片上传: {}", e.getMessage(), e);
            throw new OssException("文件流操作失败，上传的文件不合法！");
        }
    }

    /**
     * 获取已上传的文件分片信息
     *
     * @param shardingUploadListDTO 上传文件的信息
     * @return 已上传的分片
     */
    @Override
    public ListPartsResult listPartsAsync(ShardingUploadListDTO shardingUploadListDTO) {
        try {
            return customMinioClient.listPartsAsync(
                    shardingUploadListDTO.getBucketName(),
                    shardingUploadListDTO.getRegion(),
                    shardingUploadListDTO.getObjectName(),
                    shardingUploadListDTO.getMaxParts(),
                    shardingUploadListDTO.getPartNumberMarker(),
                    shardingUploadListDTO.getUploadId(),
                    shardingUploadListDTO.extraHeadersConvert(headers -> {
                        if (MapUtil.isEmpty(headers)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        headers.forEach(multimap::put);
                        return multimap;
                    }),
                    shardingUploadListDTO.extraQueryParamsConvert(extraQueryParams -> {
                        if (MapUtil.isEmpty(extraQueryParams)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        extraQueryParams.forEach(multimap::put);
                        return multimap;
                    })
            ).get().result();
        } catch (Exception e) {
            log.error("获取分片列表失败: {}", e.getMessage(), e);
            throw new OssException("获取分片列表失败");
        }
    }

    /**
     * 合并分片
     *
     * @param uploadMergeDTO 上传文件的信息
     * @return {@link Boolean} true 成功
     */
    @Override
    public Boolean completeMultipartUploadAsync(ShardingUploadMergeDTO<Part> uploadMergeDTO) {
        try {
            customMinioClient.completeMultipartUploadAsync(
                    uploadMergeDTO.getBucketName(),
                    uploadMergeDTO.getRegion(),
                    uploadMergeDTO.getObjectName(),
                    uploadMergeDTO.getUploadId(),
                    uploadMergeDTO.getParts(),
                    uploadMergeDTO.extraHeadersConvert(headers -> {
                        if (MapUtil.isEmpty(headers)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        headers.forEach(multimap::put);
                        return multimap;
                    }),
                    uploadMergeDTO.extraQueryParamsConvert(extraQueryParams -> {
                        if (MapUtil.isEmpty(extraQueryParams)) return null;
                        Multimap<String, String> multimap = HashMultimap.create();
                        extraQueryParams.forEach(multimap::put);
                        return multimap;
                    })
            ).get();
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("合并分片失败: {}", e.getMessage(), e);
            throw new OssException(e);
        }
    }


}
