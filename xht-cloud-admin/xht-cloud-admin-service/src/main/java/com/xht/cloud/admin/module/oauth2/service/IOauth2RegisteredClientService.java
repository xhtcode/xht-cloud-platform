package com.xht.cloud.admin.module.oauth2.service;

import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientCreateRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientQueryRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientUpdateRequest;
import com.xht.cloud.admin.module.oauth2.domain.response.Oauth2RegisteredClientResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
public interface IOauth2RegisteredClientService {

    /**
     * 创建
     *
     * @param createRequest {@link Oauth2RegisteredClientCreateRequest}
     * @return {@link String} 主键
     */
    String create(Oauth2RegisteredClientCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link Oauth2RegisteredClientUpdateRequest}
     */
    void update(Oauth2RegisteredClientUpdateRequest updateRequest);

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
     * @return {@link Oauth2RegisteredClientResponse}
     */
    Oauth2RegisteredClientResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link Oauth2RegisteredClientQueryRequest}
     * @return {@link PageResponse<Oauth2RegisteredClientResponse>} 分页详情
     */
    PageResponse<Oauth2RegisteredClientResponse> findPage(Oauth2RegisteredClientQueryRequest queryRequest);

}
