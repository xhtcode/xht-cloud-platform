package com.xht.cloud.admin.module.dept.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：岗位信息-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysPositionRequest(岗位信息-查询请求信息)")
public class SysPositionQueryRequest extends PageQueryRequest {

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
     * 状态（1正常0停用）
     */
    @Schema(description = "状态（1正常0停用）")
    private Integer status;

}
