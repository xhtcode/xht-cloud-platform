package com.xht.cloud.generate.module.config.service;

import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;

import java.util.List;

/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
public interface IGenCodeConfigService{

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeConfigCreateRequest}
     * @return {@link Long} 主键
     */
    Long create(GenCodeConfigCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeConfigUpdateRequest}
     */
    void update(GenCodeConfigUpdateRequest updateRequest);

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
     * @return {@link GenCodeConfigResponse}
     */
    GenCodeConfigResponse findById(String id);

    /**
     * 分页查询
     * @param queryRequest {@link GenCodeConfigQueryRequest}
     * @return {@link GenCodeConfigResponse} 分页详情
     */
    PageResponse<GenCodeConfigResponse> findPage(GenCodeConfigQueryRequest queryRequest);

    List<GenCodeConfigResponse> list();
}
