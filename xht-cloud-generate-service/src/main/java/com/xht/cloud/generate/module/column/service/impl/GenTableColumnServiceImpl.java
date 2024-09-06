package com.xht.cloud.generate.module.column.service.impl;

import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.generate.module.column.convert.GenTableColumnConvert;
import com.xht.cloud.generate.module.column.dao.GenTableColumnDao;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnCreateRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnQueryRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.domain.response.GenTableColumnResponse;
import com.xht.cloud.generate.module.column.service.IGenTableColumnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableColumnServiceImpl implements IGenTableColumnService {

    private final GenTableColumnDao genTableColumnDao;

    private final GenTableColumnConvert genTableColumnConvert;

    /**
     * 创建
     *
     * @param createRequest {@link GenTableColumnCreateRequest}
     * @return {@link Long} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenTableColumnCreateRequest createRequest) {
        GenTableColumnDO entity = genTableColumnConvert.toDO(createRequest);
        genTableColumnDao.save(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenTableColumnUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenTableColumnUpdateRequest updateRequest) {
        genTableColumnDao.updateRequest(updateRequest);
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genTableColumnDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenTableColumnResponse}
     */
    @Override
    public GenTableColumnResponse findById(String id) {
        return genTableColumnConvert.toResponse(genTableColumnDao.getById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableColumnQueryRequest}
     * @return {@link GenTableColumnResponse} 分页详情
     */
    @Override
    public PageResponse<GenTableColumnResponse> findPage(GenTableColumnQueryRequest queryRequest) {
        return genTableColumnConvert.toPageResponse(genTableColumnDao.pageQueryRequest(queryRequest));
    }

}
