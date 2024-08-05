package com.xht.cloud.framework.minio.convert;


import com.xht.cloud.framework.file.convert.BucketConvert;
import com.xht.cloud.framework.file.domain.dto.BucketDTO;
import io.minio.messages.Bucket;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 描述 ： 存储桶对象转换
 *
 * @author 小糊涂
 **/
public final class MinioBucketConvertImpl implements BucketConvert<Bucket> {

    /**
     * 存储桶转换
     *
     * @param bucket 存储桶
     */
    @Override
    public BucketDTO convert(Bucket bucket) {
        Optional<Bucket> optional = Optional.ofNullable(bucket);
        return optional.map(item -> {
            BucketDTO domain = new BucketDTO();
            domain.setBucketName(bucket.name());
            Optional.ofNullable(bucket.creationDate()).ifPresent(zonedDateTime ->
                    domain.setCreateTime(LocalDateTime.from(zonedDateTime))
            );
            return domain;
        }).orElse(null);
    }
}
