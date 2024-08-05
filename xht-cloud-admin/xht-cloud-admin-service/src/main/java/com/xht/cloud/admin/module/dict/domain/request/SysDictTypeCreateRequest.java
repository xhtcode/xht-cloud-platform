package com.xht.cloud.admin.module.dict.domain.request;

import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.core.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：字典-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictRequest(字典-增加请求信息)")
public class SysDictTypeCreateRequest extends CreateRequest {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @NotBlank(message = "字典类型 `dictType` 不能为空")
    private String dictType;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称 `dictName` 不能为空")
    private String dictName;

    /**
     * 字典类型状态
     */
    @Schema(description = "字典类型状态")
    @NotBlank(message = "字典类型状态 `dictStatus` 不能为空")
    private DictStatusEnums dictStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String dictDesc;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer dictSort;
}
