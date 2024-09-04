package com.xht.cloud.generate.module.type.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成器-字段类型对应-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenColumnTypeRequest(代码生成器-字段类型对应-查询请求信息)", description = "代码生成器-字段类型对应-查询请求信息")
public class GenColumnTypeQueryRequest extends PageQueryRequest {

    /**
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    private String dbLabel;

    /**
     * 数据库字段类型
     */
    @Schema(description = "数据库字段类型")
    private String dbValue;
}
