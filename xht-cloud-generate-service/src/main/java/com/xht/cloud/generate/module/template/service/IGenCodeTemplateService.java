package com.xht.cloud.generate.module.template.service;

 import com.xht.cloud.framework.core.domain.response.PageResponse;
 import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateCreateRequest;
 import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateQueryRequest;
 import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateUpdateRequest;
 import com.xht.cloud.generate.module.template.domain.response.GenCodeTemplateResponse;
 import java.util.List;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
public interface IGenCodeTemplateService{

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeTemplateCreateRequest}
     * @return {@link String} 主键
     */
    String create(GenCodeTemplateCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeTemplateUpdateRequest}
     */
    void update(GenCodeTemplateUpdateRequest updateRequest);

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
     * @return {@link GenCodeTemplateResponse}
     */
    GenCodeTemplateResponse findById(String id);

    /**
     * 分页查询
     * @param queryRequest {@link GenCodeTemplateQueryRequest}
     * @return {@link PageResponse<GenCodeTemplateResponse>} 分页详情
     */
    PageResponse<GenCodeTemplateResponse> findPage(GenCodeTemplateQueryRequest queryRequest);

}
