package com.xht.cloud.generate.module.database.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseCreateRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseQueryRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseUpdateRequest;
import com.xht.cloud.generate.module.database.domain.response.GenDatabaseResponse;
import com.xht.cloud.generate.module.database.convert.GenDatabaseConvert;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.database.mapper.GenDatabaseMapper;
import com.xht.cloud.generate.module.database.domain.wrapper.GenDatabaseWrapper;
import com.xht.cloud.generate.module.database.service.IGenDatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：代码生成器-数据源管理
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenDatabaseServiceImpl implements IGenDatabaseService {

    private final GenDatabaseMapper genDatabaseMapper;

    private final GenDatabaseConvert genDatabaseConvert;

    /**
     * 创建
     *
     * @param createRequest {@link GenDatabaseCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(GenDatabaseCreateRequest createRequest) {
        GenDatabaseDO entity = genDatabaseConvert.toDO(createRequest);
        genDatabaseMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenDatabaseUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenDatabaseUpdateRequest updateRequest) {
        genDatabaseMapper.updateById(genDatabaseConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genDatabaseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenDatabaseResponse}
     */
    @Override
    public GenDatabaseResponse findById(String id) {
        return genDatabaseConvert.toResponse(genDatabaseMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link PageResponse<GenDatabaseResponse>} 分页详情
     */
    @Override
    public PageResponse<GenDatabaseResponse> findPage(GenDatabaseQueryRequest queryRequest) {
        IPage<GenDatabaseDO> genDatabaseIPage = genDatabaseMapper.selectPage(PageTool.getPage(queryRequest), GenDatabaseWrapper.getInstance().lambdaQuery(genDatabaseConvert.toDO(queryRequest)));
        return genDatabaseConvert.toPageResponse(genDatabaseIPage);
    }

    /**
     * 查询集合
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link PageResponse<GenDatabaseResponse>} 分页详情
     */
    @Override
    public List<GenDatabaseResponse> list(GenDatabaseQueryRequest queryRequest) {
        List<GenDatabaseDO> genDatabaseIPage = genDatabaseMapper.selectList(GenDatabaseWrapper.getInstance().lambdaQuery(genDatabaseConvert.toDO(queryRequest)));
        return genDatabaseConvert.toResponse(genDatabaseIPage);
    }

}
