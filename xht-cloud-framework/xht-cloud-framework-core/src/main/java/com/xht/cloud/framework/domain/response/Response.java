package com.xht.cloud.framework.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述 ：公共响应实体
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "响应实体")
public abstract class Response extends AbstractResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建用户
     */
    @Schema(description = "创建用户")
    private String createBy;

    /**
     * 修改用户
     */
    @Schema(description = "修改用户")
    private String updateBy;
}
