package com.xht.cloud.generate.module.type.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成器-字段类型对应
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenColumnTypeResponse(代码生成器-字段类型对应-响应信息)", description = "代码生成器-字段类型对应")
public class GenColumnTypeResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

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

    /**
     * 语言类型
     */
    @Schema(description = "语言类型")
    private String label;

    /**
     * 代码类型
     */
    @Schema(description = "代码类型")
    private String value;

}
