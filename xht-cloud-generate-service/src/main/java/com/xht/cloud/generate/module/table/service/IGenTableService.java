package com.xht.cloud.generate.module.table.service;

import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.table.domain.request.GenTableCreateRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableUpdateRequest;
import com.xht.cloud.generate.module.table.domain.request.ImportRequest;
import com.xht.cloud.generate.module.table.domain.response.GenTableResponse;
import com.xht.cloud.generate.module.table.domain.response.GenerateVo;

import java.util.List;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
public interface IGenTableService {

    /**
     * 创建
     *
     * @param createRequest {@link GenTableCreateRequest}
     * @return {@link String} 主键
     */
    String create(GenTableCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenTableUpdateRequest}
     */
    void update(GenTableUpdateRequest updateRequest);

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
     * @return {@link GenerateVo}
     */
    GenerateVo findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableQueryRequest}
     * @return {@link PageResponse<GenTableResponse>} 分页详情
     */
    PageResponse<GenTableResponse> findPage(GenTableQueryRequest queryRequest);


    /**
     * 从数据库把表信息同步到gen_table表信息中
     *
     * @param request 请求信息
     * @return Boolean true/false
     */
    Boolean importTable(final ImportRequest request);

    /**
     * 从数据库把表信息同步到gen_table表信息中
     *
     * @param tableId 表Id
     * @return Boolean true/false
     */
    Boolean synchronous(final String tableId);

    /**
     * 获取未进行同步的表
     *
     * @param importRequest 表名
     * @return R
     */
    List<GenTableResponse> syncList(ImportRequest importRequest);
}
