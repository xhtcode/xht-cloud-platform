package com.xht.cloud.file.service;

import com.xht.cloud.file.domain.request.ShardingUploadFileRequest;
import com.xht.cloud.file.domain.request.ShardingUploadInitRequest;
import com.xht.cloud.file.domain.request.ShardingUploadMergeRequest;
import com.xht.cloud.file.domain.response.ShardingUploadResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述 ：oss存储 文件操作
 *
 * @author 小糊涂
 **/
public interface IMinioFileService {

    /**
     * 单文件上传
     *
     * @param multipartFile {@link MultipartFile} 上传文件信息
     */
    void singleUploadFile(MultipartFile multipartFile);


    /**
     * 文件分片上传初始化
     *
     * @param shardingUploadInitRequest 分片上传初始化信息
     * @return {@link String}文件上传id
     */
    ShardingUploadResponse shardingUploadInit(ShardingUploadInitRequest shardingUploadInitRequest);

    /**
     * 文件分片上传
     *
     * @param uploadFileRequest 文件分片上传信息
     * @return {@link Boolean} true上传成功
     */
    ShardingUploadResponse shardingUploadFile(ShardingUploadFileRequest uploadFileRequest);

    /**
     * 文件分片上传合并
     *
     * @param uploadMergeRequest {@link ShardingUploadMergeRequest} 文件分片上传合并请求信息
     * @return {@link Boolean} true合并成功
     */
    Boolean shardingUploadMerge(ShardingUploadMergeRequest uploadMergeRequest);

}
