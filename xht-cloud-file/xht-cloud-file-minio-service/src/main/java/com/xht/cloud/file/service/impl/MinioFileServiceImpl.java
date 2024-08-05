package com.xht.cloud.file.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.file.domain.dataobject.SysMetadataFileDO;
import com.xht.cloud.file.domain.request.ShardingUploadFileRequest;
import com.xht.cloud.file.domain.request.ShardingUploadInitRequest;
import com.xht.cloud.file.domain.request.ShardingUploadMergeRequest;
import com.xht.cloud.file.domain.response.ShardingUploadResponse;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.file.manager.ShardingFileManager;
import com.xht.cloud.file.mapper.SysMetadataFileMapper;
import com.xht.cloud.file.service.IMinioFileService;
import com.xht.cloud.framework.file.exception.OssException;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.upload.UploadFileBuilder;
import com.xht.cloud.framework.file.upload.helper.MultipartFileHelper;
import com.xht.cloud.framework.file.utils.DataSizeUtils;
import com.xht.cloud.framework.minio.service.MinioOssTemplate;
import com.xht.cloud.framework.utils.secret.MD5Utils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

/**
 * 描述 ：minio文件操作
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioFileServiceImpl implements IMinioFileService {

    private final MinioOssTemplate minioOssTemplate;

    @Resource
    private final SysMetadataFileMapper metadataFileMapper;

    private final ShardingFileManager shardingFileManager;

    /**
     * 单文件上传
     *
     * @param multipartFile {@link MultipartFile} 上传文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void singleUploadFile(MultipartFile multipartFile) {
        UploadFileBO uploadFileBO = MultipartFileHelper.init(multipartFile).format((item) -> item.bucket("test3"));
        String objectId = IdUtil.objectId();
        minioOssTemplate.putObject(uploadFileBO);
        SysMetadataFileDO sysMetadataFileDO = new SysMetadataFileDO();
        sysMetadataFileDO.setFileKey(objectId);
        sysMetadataFileDO.setUploadId(objectId);
        sysMetadataFileDO.setBucket(uploadFileBO.getBucket());
        sysMetadataFileDO.setBucketPath(uploadFileBO.getBucketPath());
        sysMetadataFileDO.setFileName(uploadFileBO.getFileName());
        sysMetadataFileDO.setFileOriginalName(uploadFileBO.getOriginalFileName());
        sysMetadataFileDO.setFileContentType(uploadFileBO.getFileContentType());
        sysMetadataFileDO.setFileSuffix(uploadFileBO.getFileSuffix());
        sysMetadataFileDO.setFileSize(uploadFileBO.getSize());
        sysMetadataFileDO.setFileStatus(FileStatusEnums.SUCCESS);
        sysMetadataFileDO.setFileMd5(uploadFileBO.md5());
        uploadFileBO.closeStream();
        metadataFileMapper.insert(sysMetadataFileDO);
    }

    /**
     * 文件分片上传初始化
     *
     * @param shardingUploadInitRequest 分片上传初始化信息
     * @return {@link String}文件上传id
     */
    @Override
    public ShardingUploadResponse shardingUploadInit(ShardingUploadInitRequest shardingUploadInitRequest) {
        UploadFileBO uploadFileBO = UploadFileBuilder.of()
                .uploadId(shardingUploadInitRequest.getUploadId())
                .bucket("test3")
                .dataSize(new DataSizeUtils(shardingUploadInitRequest.getFileSizeCount()))
                .originalFileName(shardingUploadInitRequest.getOriginalFileName())
                .fileType(shardingUploadInitRequest.getContentType())
                .build();
        if (!shardingFileManager.checkUploadInit(uploadFileBO.getUploadId())) {
            shardingFileManager.shardingUploadInit(uploadFileBO, shardingUploadInitRequest.getFileMd5());
        }
        ShardingUploadResponse response = new ShardingUploadResponse();
        response.setUploadId(uploadFileBO.getUploadId());
        response.setFileName(uploadFileBO.getFileName());
        uploadFileBO.closeStream();
        return response;
    }

    /**
     * 文件分片上传
     *
     * @param uploadFileRequest 文件分片上传信息
     * @return {@link Boolean} true上传成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShardingUploadResponse shardingUploadFile(ShardingUploadFileRequest uploadFileRequest) {
        String uploadId = uploadFileRequest.getUploadId();
        String fileKey = MD5Utils.generateSignature(uploadId);
        String uploadKey = String.format("oss:minio:%s", fileKey);
        ShardingUploadResponse response = new ShardingUploadResponse();
        response.setUploadId(uploadId);
        response.setFileName(uploadFileRequest.getFileName());
        response.setShardingIndex(uploadFileRequest.getShardingIndex());

        if (shardingFileManager.checkShardingExist(uploadFileRequest.getUploadId(), uploadFileRequest.getShardingIndex())) {
            return response;
        }
        UploadFileBO uploadFileBO = MultipartFileHelper.init(uploadFileRequest.getFile())
                .format(item -> item
                        .fileName(uploadFileRequest.getFileName())
                        .uploadId(uploadFileRequest.getUploadId())
                        .originalFileName(uploadFileRequest.getOriginalFileName())
                        .fileType(uploadFileRequest.getContentType())
                        .bucket("test3"));
        try {
            shardingFileManager.shardingUploadFile(uploadKey, uploadFileBO, uploadFileRequest);
        } catch (Exception e) {
            log.info("文件上传失败 uploadId{}", uploadFileRequest.getUploadId(), e);
            throw new OssException("文件上传失败!");
        } finally {
            uploadFileBO.closeStream();
        }
        return response;
    }

    /**
     * 文件分片上传合并
     *
     * @param uploadMergeRequest {@link ShardingUploadMergeRequest} 文件分片上传合并请求信息
     * @return {@link Boolean} true合并成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean shardingUploadMerge(ShardingUploadMergeRequest uploadMergeRequest) {
        String uploadId = uploadMergeRequest.getUploadId();
        String fileKey = MD5Utils.generateSignature(uploadId);
        String uploadKey = String.format("oss:minio:%s", fileKey);
        LambdaQueryWrapper<SysMetadataFileDO> sysMetadataFileDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMetadataFileDOLambdaQueryWrapper
                .select(
                        SysMetadataFileDO::getId,
                        SysMetadataFileDO::getUploadId,
                        SysMetadataFileDO::getBucketPath,
                        SysMetadataFileDO::getFileName,
                        SysMetadataFileDO::getFileSuffix,
                        SysMetadataFileDO::getFileContentType,
                        SysMetadataFileDO::getFileMd5
                )
                .eq(SysMetadataFileDO::getFileKey, fileKey)
                .eq(SysMetadataFileDO::getFileStatus, FileStatusEnums.SHARDING_CENTER);
        SysMetadataFileDO metadataFileDO = metadataFileMapper.selectOne(sysMetadataFileDOLambdaQueryWrapper);
        if (Objects.isNull(metadataFileDO)) {
            throw new OssException("文件源数据丢失!");
        }
        String shardingMD5 = shardingFileManager.getShardingMD5(uploadKey);
        if (!Objects.equals(metadataFileDO.getFileMd5(), shardingMD5)) {
            throw new RuntimeException("文件源信息出错，请重新上传文件");
        }
        String objectName = String.format("%s%s.%s", metadataFileDO.getBucketPath(), metadataFileDO.getFileName(), metadataFileDO.getFileSuffix());
        InputStream inputStream = shardingFileManager.shardingUploadMerge(uploadId, uploadKey, "test3", objectName);
        if (Objects.nonNull(inputStream)) {
            LambdaUpdateWrapper<SysMetadataFileDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper
                    .eq(SysMetadataFileDO::getId, metadataFileDO.getId())
                    .set(SysMetadataFileDO::getFileStatus, FileStatusEnums.SUCCESS)
                    .set(SysMetadataFileDO::getFileMd5, MD5Utils.generateSignature(IoUtil.readBytes(inputStream, true)))
            ;
            metadataFileMapper.update(lambdaUpdateWrapper);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
