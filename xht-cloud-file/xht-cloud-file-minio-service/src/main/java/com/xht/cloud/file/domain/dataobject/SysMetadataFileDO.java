package com.xht.cloud.file.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：文件元数据信息表
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_metadata_file")
public class SysMetadataFileDO extends BaseDO {

    /**
     * 主键ID，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件key
     */
    @TableField(value = "file_key")
    private String fileKey;

    /**
     * 文件上传id
     */
    @TableField(value = "upload_id")
    private String uploadId;

    /**
     * 桶名称
     */
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 存储桶路径
     */
    @TableField(value = "bucket_path")
    private String bucketPath;

    /**
     * 文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 上传文件名
     */
    @TableField(value = "file_original_name")
    private String fileOriginalName;

    /**
     * MIME类型
     */
    @TableField(value = "file_content_type")
    private String fileContentType;

    /**
     * 文件后缀
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private long fileSize;

    /**
     * 文件状态
     */
    @TableField(value = "file_status")
    private FileStatusEnums fileStatus;

    /**
     * 文件md5
     */
    @TableField(value = "file_md5")
    private String fileMd5;

    /**
     * 分片的数量
     */
    @TableField(value = "sharding_count")
    private String shardingCount;

}
