package com.xht.cloud.framework.file.oss;

import com.xht.cloud.framework.file.sharding.ShardingUploadFileDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadInitDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadListDTO;
import com.xht.cloud.framework.file.sharding.ShardingUploadMergeDTO;

/**
 * 描述 ：分片存储操作
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public interface OssShardingOperations<T, E> {

    /**
     * 文件分片上传初始化
     *
     * @param uploadInit {@link ShardingUploadInitDTO} 分片下载
     * @return {@link String} 上传id
     */
    String createMultipartUploadAsync(ShardingUploadInitDTO uploadInit);

    /**
     * 文件分片上传
     *
     * @param shardingUploadFileDTO 文件信息
     * @return {@link Boolean} true 成功
     */
    Boolean uploadPartAsync(ShardingUploadFileDTO shardingUploadFileDTO);

    /**
     * 获取已上传的文件分片信息
     *
     * @param shardingUploadListDTO 上传文件的信息
     * @return 已上传的分片
     */
    T listPartsAsync(ShardingUploadListDTO shardingUploadListDTO);

    /**
     * 合并分片
     *
     * @param uploadMergeDTO 上传文件的信息
     * @return {@link Boolean} true 成功
     */
    Boolean completeMultipartUploadAsync(ShardingUploadMergeDTO<E> uploadMergeDTO);

}
