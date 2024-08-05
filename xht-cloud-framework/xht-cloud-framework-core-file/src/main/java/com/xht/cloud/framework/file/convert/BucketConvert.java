package com.xht.cloud.framework.file.convert;

import com.xht.cloud.framework.file.domain.dto.BucketDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 描述 ：存储桶转换
 *
 * @author 小糊涂
 **/
public interface BucketConvert<T> {

    /**
     * 存储桶转换
     */
    BucketDTO convert(T bucket);

    /**
     * 存储桶转换
     */
    default List<BucketDTO> convert(Collection<T> buckets) {
        if (CollectionUtils.isEmpty(buckets)) {
            return null;
        }
        List<BucketDTO> list = new ArrayList<BucketDTO>(buckets.size());
        for (T t : buckets) {
            list.add(convert(t));
        }
        return list;
    }

}
