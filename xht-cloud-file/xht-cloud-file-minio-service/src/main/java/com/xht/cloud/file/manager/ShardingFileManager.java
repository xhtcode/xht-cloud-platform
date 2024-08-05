package com.xht.cloud.file.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.file.domain.dataobject.SysMetadataFileDO;
import com.xht.cloud.file.domain.request.ShardingUploadFileRequest;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.file.mapper.SysMetadataFileMapper;
import com.xht.cloud.framework.file.exception.OssException;
import com.xht.cloud.framework.file.sharding.ShardingUploadFileDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadInitDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadListDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadMergeDTO;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.minio.service.MinioOssTemplate;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.secret.MD5Utils;
import com.xht.cloud.framework.utils.support.StringUtils;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import static com.xht.cloud.file.enums.FileStatusEnums.SHARDING_CENTER;
import static com.xht.cloud.framework.file.constant.OssFileHeaderConstant.CONTENT_TYPE;

/**
 * 描述 ：文件分片信息表
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ShardingFileManager {

    private final MinioOssTemplate minioOssTemplate;

    private final SysMetadataFileMapper sysMetadataFileMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 校验改上传id是否已经上传过 不包含上传成功的
     *
     * @param uploadId 上传id
     * @return {@link Boolean}
     */
    public Boolean checkUploadInit(String uploadId) {
        if (StringUtils.isEmpty(uploadId)) return Boolean.FALSE;
        LambdaQueryWrapper<SysMetadataFileDO> sysMetadataFileDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMetadataFileDOLambdaQueryWrapper.eq(SysMetadataFileDO::getUploadId, uploadId).eq(SysMetadataFileDO::getFileStatus, FileStatusEnums.SHARDING_CENTER);
        return SqlHelper.exist(sysMetadataFileMapper.selectCount(sysMetadataFileDOLambdaQueryWrapper));

    }

    /**
     * 文件分片上传初始化
     *
     * @param uploadFileBO {@link UploadFileBO} 上传信息
     */
    public void shardingUploadInit(UploadFileBO uploadFileBO, String fileMD5) {
        ShardingUploadInitDTO.ShardingUploadInitDTOBuilder builder = ShardingUploadInitDTO.builder()
                .bucketName(uploadFileBO.getBucket())
                .objectName(uploadFileBO.getObjectName())
                .extraHeader(CONTENT_TYPE, uploadFileBO.getFileContentType());
        String uploadId = minioOssTemplate.createMultipartUploadAsync(builder.build());
        SysMetadataFileDO metadataFileDO = new SysMetadataFileDO();
        metadataFileDO.setFileKey(MD5Utils.generateSignature(uploadId));
        metadataFileDO.setUploadId(uploadId);
        metadataFileDO.setBucket(uploadFileBO.getBucket());
        metadataFileDO.setBucketPath(uploadFileBO.getBucketPath());
        metadataFileDO.setFileName(uploadFileBO.getFileName());
        metadataFileDO.setFileOriginalName(uploadFileBO.getOriginalFileName());
        metadataFileDO.setFileContentType(uploadFileBO.getFileContentType());
        metadataFileDO.setFileSuffix(uploadFileBO.getFileSuffix());
        metadataFileDO.setFileSize(uploadFileBO.getSize());
        metadataFileDO.setFileStatus(SHARDING_CENTER);
        metadataFileDO.setFileMd5(fileMD5);
        uploadFileBO.setUploadId(uploadId);
        sysMetadataFileMapper.insert(metadataFileDO);
    }

    /**
     * 文件分片上传
     *
     * @param uploadFileRequest 文件分片上传信息
     */
    public void shardingUploadFile(String uploadKey, UploadFileBO uploadFileBO, ShardingUploadFileRequest uploadFileRequest) {
        String uploadId = uploadFileRequest.getUploadId();
        int shardingIndex = uploadFileRequest.getShardingIndex();
        ShardingUploadFileDTO.ShardingUploadFileDTOBuilder builder = ShardingUploadFileDTO.builder();
        builder.bucketName(uploadFileBO.getBucket())
                .objectName(uploadFileBO.getObjectName())
                .data(uploadFileBO.getInputStream())
                .length(uploadFileBO.getSize())
                .uploadId(uploadId)
                .partIndex(shardingIndex);
        if (minioOssTemplate.uploadPartAsync(builder.build())) {
            redisTemplate.opsForZSet().add(uploadKey, uploadFileRequest.getShardingMD5(), shardingIndex);
        }
    }


    /**
     * 判断是否存在 true 存在 false 不存在
     *
     * @param uploadId      上传id
     * @param shardingIndex 分片索引
     * @return true 存在 false 不存在
     */
    public Boolean checkShardingExist(String uploadId, int shardingIndex) {
        return !CollectionUtils.isEmpty(redisTemplate.opsForZSet().reverseRangeByScore(uploadId, shardingIndex, shardingIndex));
    }

    /**
     * 上传文件合并
     *
     * @param uploadId   文件上传id
     * @param objectName 文件上传objectName
     * @return {@link Boolean} true 成功
     */
    public InputStream shardingUploadMerge(String uploadId, String uploadKey, String bucketName, String objectName) {
        Part[] array = listPartsAsync(uploadId, bucketName, objectName);
        ShardingUploadMergeDTO.ShardingUploadMergeDTOBuilder<Part> builder = ShardingUploadMergeDTO
                .<Part>builder().bucketName(bucketName)
                .objectName(objectName)
                .uploadId(uploadId)
                .parts(array);
        if (minioOssTemplate.completeMultipartUploadAsync(builder.build())) {
            redisTemplate.delete(uploadKey);
            return minioOssTemplate.getObject(bucketName, objectName);
        }
        return null;
    }

    private Part[] listPartsAsync(String uploadId, String bucketName, String objectName) {
        ShardingUploadListDTO.ShardingUploadListDTOBuilder builder = ShardingUploadListDTO.builder()
                .bucketName(bucketName)
                .objectName(objectName)
                .uploadId(uploadId);
        ListPartsResult listPartsResult = minioOssTemplate.listPartsAsync(builder.build());
        List<Part> parts = listPartsResult.partList();
        return parts.toArray(new Part[]{});
    }


    /**
     * 获取分片文件的总MD5
     *
     * @param uploadKey 上传文件的key
     * @return {@link String} 文件MD5
     */
    public String getShardingMD5(String uploadKey) {
        Set<Object> range = redisTemplate.opsForZSet().range(uploadKey, 0, -1);
        if (CollectionUtils.isEmpty(range)) throw new OssException("文件分片信息查询不到");
        StringBuilder fileMd5 = new StringBuilder();
        range.forEach(fileMd5::append);
        return MD5Utils.generateSignature(fileMd5.toString());
    }
}
