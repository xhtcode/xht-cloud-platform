package com.xht.cloud.admin.module.permissions.domain.request;

import com.xht.cloud.framework.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：菜单权限表-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysMenuQueryRequest(菜单权限表-查询请求信息)")
public class SysMenuQueryRequest extends PageQueryRequest {

    /**
     * 上级菜单id
     */
    @Schema(description = "上级菜单id")
    private String parentId;

    /**
     * 菜单类型（M目录C菜单F按钮）
     */
    @Schema(description = "菜单类型（M目录C菜单F按钮）")
    private String menuType;

    /**
     * 菜单类型（M目录C菜单F按钮）
     */
    @Schema(description = "菜单类型（M目录C菜单F按钮）")
    private List<String> notMenuType;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;


    /**
     * 菜单状态（1正常）
     */
    @Schema(description = "菜单状态（1正常）")
    private String menuStatus;



}
