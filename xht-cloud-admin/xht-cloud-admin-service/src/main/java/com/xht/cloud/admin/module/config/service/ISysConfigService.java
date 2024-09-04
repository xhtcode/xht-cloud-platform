package com.xht.cloud.admin.module.config.service;

import com.xht.cloud.admin.module.config.domain.request.SysConfigCreateRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigQueryRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigUpdateRequest;
import com.xht.cloud.admin.module.config.domain.response.SysConfigResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：系统配置信息
 *
 * @author 小糊涂
 **/
public interface ISysConfigService {

    /**
     * 创建
     *
     * @param createRequest {@link SysConfigCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysConfigCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysConfigUpdateRequest}
     */
    void update(SysConfigUpdateRequest updateRequest);

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
     * @return {@link SysConfigResponse}
     */
    SysConfigResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysConfigQueryRequest}
     * @return {@link SysConfigResponse} 分页详情
     */
    PageResponse<SysConfigResponse> findPage(SysConfigQueryRequest queryRequest);

}
