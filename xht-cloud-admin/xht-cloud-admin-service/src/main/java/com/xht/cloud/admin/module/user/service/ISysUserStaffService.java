package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserStaffResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
public interface ISysUserStaffService {

    /**
     * 创建
     *
     * @param createRequest {@link SysUserStaffCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysUserStaffCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param request {@link SysUserStaffUpdateRequest}
     */
    Boolean update(SysUserStaffUpdateRequest request);

    /**
     * 删除
     *
     * @param userIds {@link List<String>} id集合
     */
    Boolean remove(List<String> userIds);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserStaffResponse}
     */
    SysUserStaffResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link SysUserStaffResponse} 分页详情
     */
    PageResponse<SysUserStaffResponse> findPage(SysUserQueryRequest queryRequest);

}
