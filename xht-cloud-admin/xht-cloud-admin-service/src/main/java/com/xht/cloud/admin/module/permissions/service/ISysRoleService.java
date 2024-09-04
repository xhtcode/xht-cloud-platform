package com.xht.cloud.admin.module.permissions.service;

import com.xht.cloud.admin.module.permissions.domain.request.SysRoleCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysRoleUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysRoleResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：系统角色表
 *
 * @author 小糊涂
 **/
public interface ISysRoleService {

    /**
     * 创建
     *
     * @param createRequest {@link SysRoleCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysRoleCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysRoleUpdateRequest}
     */
    void update(SysRoleUpdateRequest updateRequest);

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysRoleResponse}
     */
    SysRoleResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysRoleQueryRequest}
     * @return {@link SysRoleResponse} 分页详情
     */
    PageResponse<SysRoleResponse> findPage(SysRoleQueryRequest queryRequest);

    /**
     * 查询全部
     *
     * @param queryRequest {@link SysRoleQueryRequest}
     * @return {@link List<SysRoleResponse>} 详情
     */
    List<SysRoleResponse> list(SysRoleQueryRequest queryRequest);
}
