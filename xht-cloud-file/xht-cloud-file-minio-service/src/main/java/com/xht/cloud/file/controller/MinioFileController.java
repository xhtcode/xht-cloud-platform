package com.xht.cloud.file.controller;

import com.xht.cloud.file.domain.request.ShardingUploadFileRequest;
import com.xht.cloud.file.domain.request.ShardingUploadInitRequest;
import com.xht.cloud.file.domain.request.ShardingUploadMergeRequest;
import com.xht.cloud.file.domain.response.ShardingUploadResponse;
import com.xht.cloud.file.service.IMinioFileService;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述 ：minio文件操作
 *
 * @author 小糊涂
 **/
@Slf4j
@Tag(name = "minio文件操作")
@RequestMapping("/oss/minio/file")
@RestController
@RequiredArgsConstructor
public class MinioFileController {

    private final IMinioFileService minioFileService;

    /**
     * 单文件上传
     *
     * @param multipartFile {@link MultipartFile} 上传文件信息
     * @return {@link Boolean}
     */
    @Operation(summary = "单文件上传")
    @PostMapping(value = "/single/upload")
    public R<Boolean> singleUploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        minioFileService.singleUploadFile(multipartFile);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 文件分片上传初始化
     *
     * @param shardingUploadInitRequest 分片上传初始化信息
     * @return {@link String}文件上传id
     */
    @Operation(summary = "文件分片上传初始化")
    @PostMapping(value = "/sharding/init")
    public R<ShardingUploadResponse> shardingUploadInit(@RequestBody ShardingUploadInitRequest shardingUploadInitRequest) {
        return R.ok(minioFileService.shardingUploadInit(shardingUploadInitRequest));
    }

    /**
     * 文件分片上传
     *
     * @param uploadFileRequest 文件分片上传信息
     * @return {@link Boolean} true上传成功
     */
    @Operation(summary = "文件分片上传")
    @PostMapping(value = "/sharding/upload")
    public R<ShardingUploadResponse> shardingUploadFile(@Validated ShardingUploadFileRequest uploadFileRequest) {
        return R.ok(minioFileService.shardingUploadFile(uploadFileRequest));
    }

    /**
     * 文件分片上传合并
     *
     * @param uploadMergeRequest {@link ShardingUploadMergeRequest} 文件分片上传合并请求信息
     * @return {@link Boolean} true合并成功
     */
    @Operation(summary = "文件分片上传合并")
    @PutMapping(value = "/sharding/merge")
    public R<Object> shardingUploadMerge(@RequestBody ShardingUploadMergeRequest uploadMergeRequest) {
        return R.ok(minioFileService.shardingUploadMerge(uploadMergeRequest));
    }


}
