package com.xht.cloud.admin.module.permissions.domain.response;

import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysRoleResponse(系统角色表-响应信息)")
public class SysRoleResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
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
    @NotNull
    private Integer dataScope;

    /**
     * 角色状态（0停用1正常）
     */
    @Schema(description = "角色状态（0停用1正常）")
    private String status;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDesc;

    private List<SysDeptResponse> depts;

}
