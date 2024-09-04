package com.xht.cloud.admin.module.dept.domain.request;

import com.xht.cloud.admin.enums.DeptStatusEnums;
import com.xht.cloud.framework.domain.request.CreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 描述 ：部门-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysDeptRequest(部门-增加请求信息)")
public class SysDeptCreateRequest extends CreateRequest {

    /**
     * 父id
     */
    @Schema(description = "父id")
    @NotBlank(message = "父id `parentId` 校验不通过")
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
    @NotBlank(message = "部门名称 `deptName` 校验不通过")
    private String deptName;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    @NotBlank(message = "部门编码 `deptCode` 校验不通过")
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
    @NotNull(message = "部门状态 `deptStatus` 校验不通过")
    private DeptStatusEnums deptStatus;

    /**
     * 部门描述
     */
    @Schema(description = "部门描述")
    private String description;
}
