package com.xht.cloud.generate.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;
import com.xht.cloud.generate.module.config.convert.GenCodeConfigConvert;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import com.xht.cloud.generate.module.config.mapper.GenCodeConfigMapper;
import com.xht.cloud.generate.module.config.domain.wrapper.GenCodeConfigWrapper;
import com.xht.cloud.generate.module.config.service.IGenCodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeConfigServiceImpl implements IGenCodeConfigService {

    private final GenCodeConfigMapper genCodeConfigMapper;

    private final GenCodeConfigConvert genCodeConfigConvert;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeConfigCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(GenCodeConfigCreateRequest createRequest) {
        GenCodeConfigDO entity = genCodeConfigConvert.toDO(createRequest);
        genCodeConfigMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenCodeConfigUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenCodeConfigUpdateRequest updateRequest) {
        genCodeConfigMapper.updateById(genCodeConfigConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genCodeConfigMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeConfigResponse}
     */
    @Override
    public GenCodeConfigResponse findById(String id) {
        return genCodeConfigConvert.toResponse(genCodeConfigMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeConfigQueryRequest}
     * @return {@link PageResponse<GenCodeConfigResponse>} 分页详情
     */
    @Override
    public PageResponse<GenCodeConfigResponse> findPage(GenCodeConfigQueryRequest queryRequest) {
        IPage<GenCodeConfigDO> genCodeConfigIPage = genCodeConfigMapper.selectPage(PageTool.getPage(queryRequest), GenCodeConfigWrapper.getInstance().lambdaQuery(genCodeConfigConvert.toDO(queryRequest)));
        return genCodeConfigConvert.toPageResponse(genCodeConfigIPage);
    }

    @Override
    public List<GenCodeConfigResponse> list() {
        List<GenCodeConfigDO> genCodeConfigDOS = genCodeConfigMapper.selectList(new LambdaQueryWrapper<GenCodeConfigDO>().select(GenCodeConfigDO::getId, GenCodeConfigDO::getConfigName, GenCodeConfigDO::getConfigDesc));
        return genCodeConfigConvert.toResponse(genCodeConfigDOS);
    }

}
