package com.xht.cloud.admin.module.dict.domain.request;

import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：字典-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictRequest(字典-查询请求信息)")
public class SysDictTypeQueryRequest extends PageQueryRequest {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称 `dictName` 不能为空")
    private String dictName;

    /**
     * 字典数据状态
     */
    @Schema(description = "字典数据状态")
    private DictStatusEnums dictStatus;

}
