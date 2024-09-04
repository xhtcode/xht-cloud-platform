package com.xht.cloud.admin.api.dict.dto;

import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictItemDTO(字典数据-响应信息)")
public class SysDictItemDTO extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 字典数据id
     */
    @Schema(description = "字典数据id")
    private String dictId;

    /**
     * 字典数据类型
     */
    @Schema(description = "字典数据类型")
    private String dictType;

    /**
     * 字典数据编码
     */
    @Schema(description = "字典数据编码")
    private String dictCode;

    /**
     * 字典数据值
     */
    @Schema(description = "字典数据值")
    private String dictValue;

    /**
     * tag展示的类型
     */
    @Schema(description = "tag展示的类型")
    private String tagType;

    /**
     * 字典数据排序
     */
    @Schema(description = "字典数据排序")
    private Integer dictSort;

    /**
     * 字典数据状态
     */
    @Schema(description = "字典数据状态")
    private DictStatusEnums dictStatus;

    /**
     * 字典数据描述
     */
    @Schema(description = "字典数据描述")
    private String dictDesc;

}
