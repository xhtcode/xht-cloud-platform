package com.xht.cloud.framework.file.oss;


import com.xht.cloud.framework.file.domain.dto.BucketDTO;
import com.xht.cloud.framework.file.domain.cmd.bucket.BaseBucketCmd;

import java.util.List;

/**
 * 描述 ：存储桶操作类
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public interface OssBucketOperations {

    /**
     * 查询所有存储桶
     *
     * @return Bucket 列表
     */
    List<BucketDTO> listBuckets();

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在，true 存在，false 不存在
     */
    default boolean bucketExists(String bucketName) {
        BaseBucketCmd bucketCmd = new BaseBucketCmd();
        bucketCmd.setBucketName(bucketName);
        return bucketExists(bucketCmd);
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketCmd 存储桶信息
     * @return 是否存在，true 存在，false 不存在
     */
    boolean bucketExists(BaseBucketCmd bucketCmd);

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    default void makeBucket(String bucketName) {
        BaseBucketCmd bucketCmd = new BaseBucketCmd();
        bucketCmd.setBucketName(bucketName);
        makeBucket(bucketCmd);
    }


    /**
     * 创建存储桶
     *
     * @param bucketCmd 存储桶信息
     */
    void makeBucket(BaseBucketCmd bucketCmd);


    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    void removeBucket(String bucketName);

}
