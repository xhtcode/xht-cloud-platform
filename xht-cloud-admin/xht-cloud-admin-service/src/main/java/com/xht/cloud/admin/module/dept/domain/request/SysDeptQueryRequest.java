package com.xht.cloud.admin.module.dept.domain.request;

import com.xht.cloud.admin.enums.DeptStatusEnums;
import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：部门-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDeptRequest(部门-查询请求信息)")
public class SysDeptQueryRequest extends PageQueryRequest {

    /**
     * 父id
     */
    @Schema(description = "父id")
    private String parentId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptCode;

    /**
     * 部门状态
     */
    @Schema(description = "部门状态")
    private DeptStatusEnums deptStatus;
}
