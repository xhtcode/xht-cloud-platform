package com.xht.cloud.framework.file.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述 ：存储桶
 *
 * @author 小糊涂
 **/
@Data
public class BucketDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 存储桶所有者 ID
     */
    private String id;

    /**
     * 存储桶所有者显示名称
     */
    private String displayName;

    /**
     * 文件数量
     */
    private long fileCount;

    /**
     * 所有文件大小
     */
    private String fileSizeCount;
}
