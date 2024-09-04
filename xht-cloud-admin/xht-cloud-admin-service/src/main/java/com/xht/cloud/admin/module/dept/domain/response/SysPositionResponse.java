package com.xht.cloud.admin.module.dept.domain.response;

import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysPositionResponse(岗位信息-响应信息)")
public class SysPositionResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
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
    private String positionCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String positionName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 状态（1正常0停用）
     */
    @Schema(description = "状态（1正常0停用）")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

}
