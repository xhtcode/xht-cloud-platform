package com.xht.cloud.admin.module.dept.domain.request;

import com.xht.cloud.framework.domain.request.UpdateRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：岗位信息-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysPositionRequest(岗位信息-修改请求信息)")
public class SysPositionUpdateRequest extends UpdateRequest {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 校验不通过")
    private String id;

    /**
     * 父id
     */
    @Schema(description = "父id")
    private String parentId;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private String deptId;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @NotBlank(message = "岗位编码 `positionCode` 校验不通过")
    private String positionCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @NotBlank(message = "岗位名称 `positionName` 校验不通过")
    private String positionName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @IntegerInterval(message = "排序 `sort` 字段值在0到999之间")
    private Integer sort;

    /**
     * 状态（1正常0停用）
     */
    @Schema(description = "状态（1正常0停用）")
    @IntegerInterval(message = "状态（1正常0停用） `status` 字段值在0到999之间")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

}
