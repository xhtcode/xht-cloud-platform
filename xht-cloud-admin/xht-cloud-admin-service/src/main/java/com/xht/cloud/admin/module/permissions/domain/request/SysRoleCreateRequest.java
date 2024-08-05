package com.xht.cloud.admin.module.permissions.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：系统角色表-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysRoleRequest(系统角色表-增加请求信息)")
public class SysRoleCreateRequest extends CreateRequest {

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称 `roleName` 校验不通过")
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码 `roleCode` 校验不通过")
    private String roleCode;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @IntegerInterval(message = "显示顺序 `roleSort` 字段值在0到999之间")
    private Integer roleSort;

    /**
     * 数据权限
     * <li class="el-select-dropdown__item"><span>1全部数据权限</span></li>
     * <li class="el-select-dropdown__item selected"><span>2自定数据权限</span></li>
     * <li class="el-select-dropdown__item hover"><span>3本部门数据权限</span></li>
     * <li class="el-select-dropdown__item"><span>4本部门及以下数据权限</span></li>
     * <li class="el-select-dropdown__item"><span>5仅本人数据权限</span></li>
     */
    @Schema(description = "数据权限")
    @NotNull(message = "数据权限 `dataScope` 校验不通过")
    private Integer dataScope;

    /**
     * 角色状态（0停用1正常）
     */
    @Schema(description = "角色状态（0停用1正常）")
    @NotBlank(message = "角色状态 `status` 校验不通过")
    private String status;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDesc;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private List<String> deptIds;
}
