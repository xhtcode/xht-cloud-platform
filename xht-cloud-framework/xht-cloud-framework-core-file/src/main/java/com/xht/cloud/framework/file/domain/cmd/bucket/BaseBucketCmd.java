package com.xht.cloud.framework.file.domain.cmd.bucket;

import com.xht.cloud.framework.file.domain.cmd.AbstractBaseOssCmd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ： 桶参数
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "桶参数")
public class BaseBucketCmd extends AbstractBaseOssCmd {

    /**
     * 存储桶名称
     */
    @Schema(description = "存储桶名称")
    private String bucketName;

    /**
     * 区域
     */
    @Schema(description = "区域")
    private String region;


}
