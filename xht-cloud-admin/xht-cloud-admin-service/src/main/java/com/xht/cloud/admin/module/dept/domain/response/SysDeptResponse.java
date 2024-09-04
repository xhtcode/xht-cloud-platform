package com.xht.cloud.admin.module.dept.domain.response;

import com.xht.cloud.admin.enums.DeptStatusEnums;
import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDeptResponse(部门-响应信息)")
public class SysDeptResponse extends Response {

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
     * 指定主管ID(用户id)
     */
    @Schema(description = "指定主管ID(用户id)")
    private String directorId;

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
     * 部门负责人
     */
    @Schema(description = "部门负责人")
    private String deptLeader;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String deptTel;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer deptSort;

    /**
     * 部门状态
     */
    @Schema(description = "部门状态")
    private DeptStatusEnums deptStatus;

    /**
     * 部门描述
     */
    @Schema(description = "部门描述")
    private String description;

}
