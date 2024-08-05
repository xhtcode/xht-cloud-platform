package com.xht.cloud.framework.minio.client;

import com.google.common.collect.Multimap;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

/**
 * 描述 ：minio自动配置
 *
 * @author 小糊涂
 * @description 在原生的{@link MinioClient}中是没有继承上面找到的那些方法的，需要重新建一个类实现并继承以上需要使用的方法。
 * 注意在新版本的minio sdk中将{@link MinioClient}一部分接口拆分到{@link MinioAsyncClient}中了，这里我们需要继承{@link MinioAsyncClient}类。
 * 这里主要用到4个方法：
 * <li>createMultipartUploadAsync(创建分片上传请求)</li>
 * <li>uploadPartAsync(分片上传)</li>
 * <li>listPartsAsync(完成分片上传，执行合并文件)</li>
 * <li>completeMultipartUploadAsync(查询分片数据)</li>
 * @see io.minio.MinioClient
 * @see io.minio.MinioAsyncClient
 **/
@Slf4j
public class CustomMinioClient extends MinioAsyncClient {

    public CustomMinioClient(MinioAsyncClient client) {
        super(client);
    }

    /**
     * 创建分片上传请求
     *
     * @param bucketName       存储桶
     * @param region           区域
     * @param objectName       对象名
     * @param headers          消息头
     * @param extraQueryParams 额外查询参数(可选)
     */
    @Override
    public CompletableFuture<CreateMultipartUploadResponse> createMultipartUploadAsync(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.createMultipartUploadAsync(bucketName, region, objectName, headers, extraQueryParams);
    }

    /**
     * 分片上传
     *
     * @param bucketName       存储桶
     * @param region           区域 (可选)
     * @param objectName       对象名.
     * @param data             对象数据必须是 {@link InputStream}, {@link RandomAccessFile},{@link  Byte[]} 或者 {@link String}.
     * @param length           对象数据长度.
     * @param uploadId         上传ID.
     * @param partNumber       分片索引.
     * @param extraHeaders     额外消息头(可选)
     * @param extraQueryParams 额外查询参数 (可选)
     */
    @Override
    public CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams);
    }

    /**
     * 完成分片上传，执行合并文件
     *
     * @param bucketName       存储桶
     * @param region           区域
     * @param objectName       对象名
     * @param uploadId         上传ID
     * @param parts            分片
     * @param extraHeaders     额外消息头(可选)
     * @param extraQueryParams 额外查询参数(可选)
     */
    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }

    /**
     * 查询分片数据
     *
     * @param bucketName       存储桶
     * @param region           区域(可选)
     * @param objectName       对象名
     * @param maxParts         获取的最大分片(可选)。
     * @param partNumberMarker 分片编号标记(可选).
     * @param uploadId         上传ID
     * @param extraHeaders     额外消息头(可选)
     * @param extraQueryParams 额外查询参数(可选)
     */
    @Override
    public CompletableFuture<ListPartsResponse> listPartsAsync(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams);
    }

}
