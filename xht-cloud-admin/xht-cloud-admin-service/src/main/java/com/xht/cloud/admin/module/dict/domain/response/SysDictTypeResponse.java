package com.xht.cloud.admin.module.dict.domain.response;

import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：字典
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictTypeResponse(字典-响应信息)")
public class SysDictTypeResponse extends Response {

    /**
     * Id
     */
    @Schema(description = "Id")
    private String id;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String dictName;

    /**
     * 字典类型状态
     */
    @Schema(description = "字典类型状态")
    private DictStatusEnums dictStatus;


    /**
     * 字典类型排序
     */
    @Schema(description = "字典类型排序")
    private Integer dictSort;

    /**
     * 字典类型备注
     */
    @Schema(description = "字典类型备注")
    private String dictDesc;


}
