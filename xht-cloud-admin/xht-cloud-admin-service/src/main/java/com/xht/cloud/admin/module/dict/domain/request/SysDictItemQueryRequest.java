package com.xht.cloud.admin.module.dict.domain.request;

import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import com.xht.cloud.framework.web.validation.group.Create;
import com.xht.cloud.framework.web.validation.group.Query;
import com.xht.cloud.framework.web.validation.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：字典数据-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDictItemRequest(字典数据-查询请求信息)")
public class SysDictItemQueryRequest extends PageQueryRequest {

    /**
     * 字典id
     */
    @Schema(description = "字典id")
    @NotBlank(message = "字典id `dictId` 不能为空", groups = {Query.class, Create.class, Update.class})
    private String dictId;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    @NotBlank(message = "字典编码 `dictCode` 不能为空")
    private String dictCode;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    @NotBlank(message = "字典值 `dictValue` 不能为空")
    private String dictValue;


    /**
     * 字典数据状态
     */
    @Schema(description = "字典数据状态")
    private DictStatusEnums dictStatus;

}
