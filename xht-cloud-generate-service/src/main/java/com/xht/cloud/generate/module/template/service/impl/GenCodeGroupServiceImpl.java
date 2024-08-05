package com.xht.cloud.generate.module.template.service.impl;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.generate.module.template.convert.GenCodeGroupConvert;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeGroupDO;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeGroupResponse;
import com.xht.cloud.generate.module.template.domain.wrapper.GenCodeGroupWrapper;
import com.xht.cloud.generate.module.template.mapper.GenCodeGroupMapper;
import com.xht.cloud.generate.module.template.mapper.GenCodeTemplateMapper;
import com.xht.cloud.generate.module.template.service.IGenCodeGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeGroupServiceImpl implements IGenCodeGroupService {

    private final GenCodeGroupMapper genCodeGroupMapper;

    private final GenCodeTemplateMapper genCodeTemplateMapper;

    private final GenCodeGroupConvert genCodeGroupConvert;
    private final static GenCodeGroupWrapper GEN_CODE_GROUP_WRAPPER = new GenCodeGroupWrapper();

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeGroupCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(GenCodeGroupCreateRequest createRequest) {
        GenCodeGroupDO entity = genCodeGroupConvert.toDO(createRequest);
        genCodeGroupMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenCodeGroupUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenCodeGroupUpdateRequest updateRequest) {
        genCodeGroupMapper.updateById(genCodeGroupConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        long l = genCodeTemplateMapper.selectCountIn(GenCodeTemplateDO::getGroupId, ids);
        Assert.isTrue(l > 0, "当前组已绑定模板，禁止删除!");
        genCodeGroupMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeGroupResponse}
     */
    @Override
    public GenCodeGroupResponse findById(String id) {
        return genCodeGroupConvert.toResponse(genCodeGroupMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeGroupQueryRequest}
     * @return {@link List<GenCodeGroupResponse>} 分页详情
     */
    @Override
    public List<GenCodeGroupResponse> findPage(GenCodeGroupQueryRequest queryRequest) {
        return genCodeGroupConvert.toResponse(genCodeGroupMapper.selectList(GEN_CODE_GROUP_WRAPPER.lambdaQuery(genCodeGroupConvert.toDO(queryRequest))));
    }

}
