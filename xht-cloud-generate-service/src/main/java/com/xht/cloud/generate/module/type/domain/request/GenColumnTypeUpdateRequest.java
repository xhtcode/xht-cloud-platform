package com.xht.cloud.generate.module.type.domain.request;

import com.xht.cloud.framework.core.domain.request.UpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：代码生成器-字段类型对应-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenColumnTypeRequest(代码生成器-字段类型对应-修改请求信息)", description = "代码生成器-字段类型对应-修改请求信息")
public class GenColumnTypeUpdateRequest extends UpdateRequest<String> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 校验不通过")
    private String id;

    /**
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    @NotBlank(message = "数据库类型 `dbLabel` 校验不通过")
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

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
