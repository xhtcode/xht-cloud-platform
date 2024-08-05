package com.xht.cloud.framework.minio.repository;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.file.enums.OssErrorStatusCode;
import com.xht.cloud.framework.file.exception.OssException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ：MinioFileTemplate
 *
 * @author 小糊涂
 **/
public class MinioFileRepository {

    private final MinioClient minioClient;

    public MinioFileRepository(MinioClient minioClient) {
        Assert.notNull(minioClient, "minioClient is not null! ");
        this.minioClient = minioClient;
    }


//---------------------------Minio Bucket 存储通基础操作服务 Start-------------------------------------

    /**
     * 查询所有存储桶
     *
     * @return Bucket 列表
     */
    public List<Bucket> listBuckets() {
        return listBuckets(null);
    }

    /**
     * 查询所有存储桶
     *
     * @param args {@link ListBucketsArgs}
     * @return Bucket 列表
     */
    public List<Bucket> listBuckets(ListBucketsArgs args) {
        try {
            List<Bucket> buckets;
            if (Objects.nonNull(args)) {
                buckets = minioClient.listBuckets(args);
            } else {
                buckets = minioClient.listBuckets();
            }
            return buckets;
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在，true 存在，false 不存在
     */
    public boolean bucketExists(String bucketName) {
        return bucketExists(bucketName, null);
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 是否存在，true 存在，false 不存在
     */
    public boolean bucketExists(String bucketName, String region) {
        return bucketExists(BucketExistsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketExistsArgs {@link BucketExistsArgs}
     * @return true 存在，false 不存在
     */
    public boolean bucketExists(BucketExistsArgs bucketExistsArgs) {
        try {
            return minioClient.bucketExists(bucketExistsArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    public void makeBucket(String bucketName) {
        makeBucket(bucketName, null);
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public void makeBucket(String bucketName, String region) {
        makeBucket(MakeBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 创建存储桶
     * <p>
     * 该方法仅仅是 Minio 原始方法的封装，不包含校验等操作。
     *
     * @param makeBucketArgs {@link MakeBucketArgs}
     */
    public void makeBucket(MakeBucketArgs makeBucketArgs) {
        try {
            minioClient.makeBucket(makeBucketArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    public void removeBucket(String bucketName) {
        removeBucket(bucketName, null);
    }

    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public void removeBucket(String bucketName, String region) {
        removeBucket(RemoveBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param removeBucketArgs {@link RemoveBucketArgs}
     */
    public void removeBucket(RemoveBucketArgs removeBucketArgs) {
        try {
            minioClient.removeBucket(removeBucketArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }


//---------------------------Minio Bucket 存储通基础操作服务 End---------------------------------------


    /**
     * 列出桶的对象信息.
     *
     * @param bucketName          存储桶名称
     * @param region              区域
     * @param delimiter           分隔符
     * @param recursive           是否递归
     * @param useUrlEncodingType  是否使用 UrlEncoding
     * @param keyMarker           关键字
     * @param maxKeys             最大关键字
     * @param prefix              前缀
     * @param includeVersions     是否包含版本
     * @param versionIdMarker     版本关键字
     * @param useApiVersion1      如果是true, 使用版本1 REST API
     * @param continuationToken   持续集成 Token
     * @param fetchOwner          获取 OwnerDomain
     * @param includeUserMetadata 包含用户自定义信息
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, boolean useApiVersion1, String continuationToken, boolean fetchOwner, boolean includeUserMetadata) {
        return listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .region(region)
                .delimiter(delimiter)
                .recursive(recursive)
                .useUrlEncodingType(useUrlEncodingType)
                .keyMarker(keyMarker)
                .maxKeys(maxKeys)
                .prefix(prefix)
                .includeVersions(includeVersions)
                .versionIdMarker(versionIdMarker)
                .useApiVersion1(useApiVersion1)
                .continuationToken(continuationToken)
                .fetchOwner(fetchOwner)
                .includeUserMetadata(includeUserMetadata)
                .build());
    }

    /**
     * listObjects列出桶的对象信息
     *
     * @param listObjectsArgs {@link ListObjectsArgs}
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(ListObjectsArgs listObjectsArgs) {
        Assert.notNull(listObjectsArgs, () -> new OssException(OssErrorStatusCode.BUSINESS_ERROR));
        return minioClient.listObjects(listObjectsArgs);
    }

    /**
     * 移除一个对象
     *
     * @param removeObjectArgs {@link RemoveObjectArgs}
     */
    public void removeObject(RemoveObjectArgs removeObjectArgs) {
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名
     * @param versionId  版本ID
     */
    public void removeObject(String bucketName, String region, String objectName, String versionId) {
        removeObject(RemoveObjectArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objects              待删除对象
     * @param bypassGovernanceMode 使用 Governance 模式
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, String region, Iterable<DeleteObject> objects, boolean bypassGovernanceMode) {
        return removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).region(region).objects(objects).bypassGovernanceMode(bypassGovernanceMode).build());
    }

    /**
     * 删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param removeObjectsArgs {@link RemoveObjectsArgs}
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Iterable<Result<DeleteError>> removeObjects(RemoveObjectsArgs removeObjectsArgs) {

        return minioClient.removeObjects(removeObjectsArgs);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param statObjectArgs {@link StatObjectArgs}
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(StatObjectArgs statObjectArgs) {
        try {
            return minioClient.statObject(statObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param composeObjectArgs {@link ComposeObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse composeObject(ComposeObjectArgs composeObjectArgs) {
        try {
            return minioClient.composeObject(composeObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param copyObjectArgs {@link CopyObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse copyObject(CopyObjectArgs copyObjectArgs) {
        try {
            return minioClient.copyObject(copyObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 恢复对象
     *
     * @param args {@link RestoreObjectArgs}
     */
    public void restoreObject(RestoreObjectArgs args) {
        try {
            minioClient.restoreObject(args);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 通过 SQL 表达式选择对象的内容
     *
     * @param selectObjectContentArgs {@link SelectObjectContentArgs}
     * @return {@link SelectResponseStream}
     */
    public SelectResponseStream selectObjectContent(SelectObjectContentArgs selectObjectContentArgs) {
        try {
            return minioClient.selectObjectContent(selectObjectContentArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 使用此方法，获取对象的上传策略（包含签名、文件信息、路径等），然后使用这些信息采用POST 方法的表单数据上传数据。
     * <p>
     * 也就是可以生成一个临时上传的信息对象，第三方可以使用这些信息，就可以上传文件。
     * <p>
     * 一般可用于，前端请求一个上传策略，后端返回给前端，前端使用Post请求+访问策略去上传文件，这可以用于JS+SDK的混合方式集成
     *
     * @param postPolicy {@link PostPolicy}
     * @return {@link  Map}
     */
    public Map<String, String> getPreSignedPostFormData(PostPolicy postPolicy) {
        try {
            return minioClient.getPresignedPostFormData(postPolicy);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @param duration   过期时间
     * @param unit       过期时间单位
     * @param versionId  版本ID
     * @return url string
     */
    public String getPreSignedObjectUrl(String bucketName, String region, String objectName, Method method, int duration, TimeUnit unit, String versionId) {
        return getPreSignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .method(method)
                .expiry(duration, unit)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，
     * <p>
     * 这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param args {@link GetPresignedObjectUrlArgs}
     * @return url string
     */
    public String getPreSignedObjectUrl(GetPresignedObjectUrlArgs args) {
        try {
            return minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        downloadObject(DownloadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .overwrite(overwrite)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载
     *
     * @param downloadObjectArgs {@link DownloadObjectArgs}
     */
    public void downloadObject(DownloadObjectArgs downloadObjectArgs) {
        try {
            minioClient.downloadObject(downloadObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param getObjectArgs {@link GetObjectArgs}
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(GetObjectArgs getObjectArgs) {
        try {
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @param sse         服务加密
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType, boolean legalHold, Retention retention, Tags tags, ServerSideEncryption sse) {
        return putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .stream(stream, objectSize, partSize)
                .contentType(contentType)
                .sse(sse)
                .legalHold(legalHold)
                .tags(tags)
                .retention(retention)
                .build());
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 GB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param putObjectArgs {@link PutObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse putObject(PutObjectArgs putObjectArgs) {
        try {
            return minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }


    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @param sse         服务加密
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention, Tags tags, ServerSideEncryption sse) throws IOException {
        return uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .contentType(contentType)
                .sse(sse)
                .legalHold(legalHold)
                .tags(tags)
                .retention(retention)
                .build());
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param uploadObjectArgs {@link UploadObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse uploadObject(UploadObjectArgs uploadObjectArgs) {
        try {
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (Exception e) {
            throw new OssException(e);
        }
    }

}
