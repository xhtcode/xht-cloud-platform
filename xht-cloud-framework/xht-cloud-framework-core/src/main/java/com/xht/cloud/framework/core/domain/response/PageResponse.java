package com.xht.cloud.framework.core.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：分页信息响应实体
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "分页信息响应实体")
public class PageResponse<T> extends AbstractResponse {

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private long current;

    /**
     * 每页显示条数
     */
    @Schema(description = "每页显示条数")
    private long size;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private long pages;

    /**
     * 总条目数
     */
    @Schema(description = "总条目数")
    private long total;

    /**
     * 结果集
     */
    @Schema(description = "结果集")
    private List<T> list;

}
