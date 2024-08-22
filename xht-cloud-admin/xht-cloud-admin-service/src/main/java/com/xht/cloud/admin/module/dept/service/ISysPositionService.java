package com.xht.cloud.admin.module.dept.service;

import com.xht.cloud.admin.module.dept.domain.request.SysPositionCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysPositionResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
public interface ISysPositionService {

    /**
     * 创建
     *
     * @param createRequest {@link SysPositionCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysPositionCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysPositionUpdateRequest}
     */
    void update(SysPositionUpdateRequest updateRequest);

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
     * @return {@link SysPositionResponse}
     */
    SysPositionResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysPositionQueryRequest}
     * @return {@link SysPositionResponse} 分页详情
     */
    PageResponse<SysPositionResponse> findPage(SysPositionQueryRequest queryRequest);

}
