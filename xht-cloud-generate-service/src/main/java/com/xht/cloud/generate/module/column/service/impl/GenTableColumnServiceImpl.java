package com.xht.cloud.generate.module.column.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.column.convert.GenTableColumnConvert;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnCreateRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnQueryRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.domain.response.GenTableColumnResponse;
import com.xht.cloud.generate.module.column.domain.wrapper.GenTableColumnWrapper;
import com.xht.cloud.generate.module.column.mapper.GenTableColumnMapper;
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

    private final GenTableColumnMapper genTableColumnMapper;

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
        genTableColumnMapper.insert(entity);
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
        genTableColumnMapper.updateById(genTableColumnConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genTableColumnMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenTableColumnResponse}
     */
    @Override
    public GenTableColumnResponse findById(String id) {
        return genTableColumnConvert.toResponse(genTableColumnMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableColumnQueryRequest}
     * @return {@link GenTableColumnResponse} 分页详情
     */
    @Override
    public PageResponse<GenTableColumnResponse> findPage(GenTableColumnQueryRequest queryRequest) {
        IPage<GenTableColumnDO> genTableColumnIPage = genTableColumnMapper.selectPage(PageTool.getPage(queryRequest), GenTableColumnWrapper.getInstance().lambdaQuery(genTableColumnConvert.toDO(queryRequest)));
        return genTableColumnConvert.toPageResponse(genTableColumnIPage);
    }

}
