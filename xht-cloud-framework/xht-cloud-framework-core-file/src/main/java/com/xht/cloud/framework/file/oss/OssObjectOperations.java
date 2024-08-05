package com.xht.cloud.framework.file.oss;

import com.xht.cloud.framework.file.constant.FileTypeConstant;
import com.xht.cloud.framework.file.constant.OssRequestMethod;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.exception.OssException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ：对象存储操作
 *
 * @author 小糊涂
 **/
public interface OssObjectOperations {

    /**
     * 上传文件
     *
     * @param uploadFileBO {@link UploadFileBO} 文件上传的参数
     */
    void putObject(UploadFileBO uploadFileBO) throws OssException;


    /**
     * 上传文件
     *
     * @param bucket 桶名称
     * @param object 文件名称
     * @param stream 文件流
     */
    default void putObject(String bucket, String object, InputStream stream) throws OssException, IOException {
        putObject(bucket, object, FileTypeConstant.DEFAULT_FILETYPE.contentType(), stream, (long) stream.available(), -1L);
    }

    /**
     * 上传文件
     *
     * @param bucket      桶名称
     * @param object      文件名称
     * @param contentType 文件类型
     * @param stream      文件流
     */
    default void putObject(String bucket, String object, String contentType, InputStream stream) throws OssException, IOException {
        putObject(bucket, object, contentType, stream, (long) stream.available(), -1L);
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
    void putObject(String bucket, String object, String contentType, InputStream stream, Long objectSize, Long partSize) throws OssException;


    /**
     * 获取文件二进制流
     *
     * @param bucket bucket名称
     * @param object 文件名称
     * @return 二进制流
     */
    InputStream getObject(String bucket, String object) throws OssException;

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     */
    void delete(String bucketName, String fileName) throws OssException;

    /**
     * 获取文件链接
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     * @return 分享的文件连接
     */
    default String getPreviewUrl(String bucketName, String fileName) throws Exception {
        return getPreviewUrl(bucketName, fileName, OssRequestMethod.GET);
    }

    /**
     * 获取文件链接
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     * @param method     请求方式
     * @return 分享的文件连接
     */
    default String getPreviewUrl(String bucketName, String fileName, OssRequestMethod method) throws Exception {
        return getPreviewUrl(bucketName, fileName, method, null, null);
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
    String getPreviewUrl(String bucketName, String fileName, OssRequestMethod method, Integer duration, TimeUnit timeUnit) throws Exception;

}
