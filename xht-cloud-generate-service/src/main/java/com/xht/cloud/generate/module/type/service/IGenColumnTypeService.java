package com.xht.cloud.generate.module.type.service;

 import com.xht.cloud.framework.core.domain.response.PageResponse;
 import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeCreateRequest;
 import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeQueryRequest;
 import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeUpdateRequest;
 import com.xht.cloud.generate.module.type.domain.response.GenColumnTypeResponse;
 import java.util.List;

/**
 * 描述 ：代码生成器-字段类型对应
 *
 * @author 小糊涂
 **/
public interface IGenColumnTypeService{

    /**
     * 创建
     *
     * @param createRequest {@link GenColumnTypeCreateRequest}
     * @return {@link String} 主键
     */
    Long create(GenColumnTypeCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenColumnTypeUpdateRequest}
     */
    void update(GenColumnTypeUpdateRequest updateRequest);

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
     * @return {@link GenColumnTypeResponse}
     */
    GenColumnTypeResponse findById(String id);

    /**
     * 分页查询
     * @param queryRequest {@link GenColumnTypeQueryRequest}
     * @return {@link GenColumnTypeResponse} 分页详情
     */
    PageResponse<GenColumnTypeResponse> findPage(GenColumnTypeQueryRequest queryRequest);

}
