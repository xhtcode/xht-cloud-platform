package com.xht.cloud.admin.module.sequence.service;

import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceUpdateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public interface ISysSequenceService {

    /**
     * 创建
     *
     * @param createRequest {@link SysSequenceCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysSequenceCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysSequenceUpdateRequest}
     */
    void update(SysSequenceUpdateRequest updateRequest);

    /**
     * 删除
     *
     * @param ids {@link String} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysSequenceResponse}
     */
    SysSequenceResponse findById(String id);


    /**
     * 分页查询
     *
     * @param queryRequest {@link SysSequenceQueryRequest}
     * @return {@link SysSequenceResponse} 分页详情
     */
    PageResponse<SysSequenceResponse> findPage(SysSequenceQueryRequest queryRequest);

}
