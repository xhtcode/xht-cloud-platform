package com.xht.cloud.generate.module.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeTemplateResponse;
import com.xht.cloud.generate.module.template.convert.GenCodeTemplateConvert;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeGroupDO;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;
import com.xht.cloud.generate.module.template.mapper.GenCodeGroupMapper;
import com.xht.cloud.generate.module.template.mapper.GenCodeTemplateMapper;
import com.xht.cloud.generate.module.template.domain.wrapper.GenCodeTemplateWrapper;
import com.xht.cloud.generate.module.template.service.IGenCodeTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeTemplateServiceImpl implements IGenCodeTemplateService {

    private final GenCodeTemplateMapper genCodeTemplateMapper;

    private final GenCodeGroupMapper genCodeGroupMapper;

    private final GenCodeTemplateConvert genCodeTemplateConvert;

    private final static GenCodeTemplateWrapper GEN_CODE_TEMPLATE_WRAPPER = new GenCodeTemplateWrapper();

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeTemplateCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(GenCodeTemplateCreateRequest createRequest) {
        GenCodeTemplateDO entity = genCodeTemplateConvert.toDO(createRequest);
        checkGroup(entity.getGroupId());
        genCodeTemplateMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenCodeTemplateUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenCodeTemplateUpdateRequest updateRequest) {
        checkGroup(updateRequest.getGroupId());
        genCodeTemplateMapper.updateById(genCodeTemplateConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genCodeTemplateMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeTemplateResponse}
     */
    @Override
    public GenCodeTemplateResponse findById(String id) {
        return genCodeTemplateConvert.toResponse(genCodeTemplateMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeTemplateQueryRequest}
     * @return {@link PageResponse<GenCodeTemplateResponse>} 分页详情
     */
    @Override
    public PageResponse<GenCodeTemplateResponse> findPage(GenCodeTemplateQueryRequest queryRequest) {
        LambdaQueryWrapper<GenCodeTemplateDO> lambdaQueryWrapper = GEN_CODE_TEMPLATE_WRAPPER.lambdaQuery(genCodeTemplateConvert.toDO(queryRequest));
        if (!StringUtils.hasText(queryRequest.getGroupId())) {
            lambdaQueryWrapper.isNull(GenCodeTemplateDO::getGroupId);
        }
        IPage<GenCodeTemplateDO> genCodeTemplateIPage = genCodeTemplateMapper.selectPage(PageTool.getPage(queryRequest), lambdaQueryWrapper);
        return genCodeTemplateConvert.toPageResponse(genCodeTemplateIPage);
    }

    private void checkGroup(String groupId) {
        long l = genCodeGroupMapper.selectCount(GenCodeGroupDO::getId, groupId);
        Assert.isTrue(l == 0, "当前组不存在!");
    }
}
