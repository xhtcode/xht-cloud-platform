package com.xht.cloud.generate.module.database.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.generate.module.database.convert.GenDatabaseConvert;
import com.xht.cloud.generate.module.database.dao.GenDatabaseDao;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseCreateRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseQueryRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseUpdateRequest;
import com.xht.cloud.generate.module.database.domain.response.GenDatabaseResponse;
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

    private final GenDatabaseDao genDatabaseDao;

    private final GenDatabaseConvert genDatabaseConvert;

    /**
     * 创建
     *
     * @param createRequest {@link GenDatabaseCreateRequest}
     * @return {@link Long} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenDatabaseCreateRequest createRequest) {
        GenDatabaseDO entity = genDatabaseConvert.toDO(createRequest);
        genDatabaseDao.save(entity);
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
        genDatabaseDao.updateRequest(updateRequest);
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genDatabaseDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenDatabaseResponse}
     */
    @Override
    public GenDatabaseResponse findById(String id) {
        return genDatabaseConvert.toResponse(genDatabaseDao.getById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link GenDatabaseResponse} 分页详情
     */
    @Override
    public PageResponse<GenDatabaseResponse> findPage(GenDatabaseQueryRequest queryRequest) {
        IPage<GenDatabaseDO> genDatabaseIPage = genDatabaseDao.pageQueryRequest(queryRequest);
        return genDatabaseConvert.toPageResponse(genDatabaseIPage);
    }

    /**
     * 查询集合
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link GenDatabaseResponse} 分页详情
     */
    @Override
    public List<GenDatabaseResponse> list(GenDatabaseQueryRequest queryRequest) {
        List<GenDatabaseDO> genDatabaseIPage = genDatabaseDao.listQueryRequest(queryRequest);
        return genDatabaseConvert.toResponse(genDatabaseIPage);
    }

}
