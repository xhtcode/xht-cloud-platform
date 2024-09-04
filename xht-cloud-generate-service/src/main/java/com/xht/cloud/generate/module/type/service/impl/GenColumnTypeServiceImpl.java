package com.xht.cloud.generate.module.type.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.type.convert.GenColumnTypeConvert;
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeCreateRequest;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeQueryRequest;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeUpdateRequest;
import com.xht.cloud.generate.module.type.domain.response.GenColumnTypeResponse;
import com.xht.cloud.generate.module.type.mapper.GenColumnTypeMapper;
import com.xht.cloud.generate.module.type.service.IGenColumnTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：代码生成器-字段类型对应
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenColumnTypeServiceImpl implements IGenColumnTypeService {

    private final GenColumnTypeMapper genColumnTypeMapper;

    private final GenColumnTypeConvert genColumnTypeConvert;

    /**
     * 创建
     *
     * @param createRequest {@link GenColumnTypeCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenColumnTypeCreateRequest createRequest) {
        GenColumnTypeDO entity = genColumnTypeConvert.toDO(createRequest);
        genColumnTypeMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenColumnTypeUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenColumnTypeUpdateRequest updateRequest) {
        genColumnTypeMapper.updateById(genColumnTypeConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genColumnTypeMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenColumnTypeResponse}
     */
    @Override
    public GenColumnTypeResponse findById(String id) {
        return genColumnTypeConvert.toResponse(genColumnTypeMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenColumnTypeQueryRequest}
     * @return {@link GenColumnTypeResponse} 分页详情
     */
    @Override
    public PageResponse<GenColumnTypeResponse> findPage(GenColumnTypeQueryRequest queryRequest) {
        LambdaQueryWrapper<GenColumnTypeDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(queryRequest.getDbLabel()), GenColumnTypeDO::getDbLabel, queryRequest.getDbLabel())
                .eq(StringUtils.hasText(queryRequest.getDbValue()), GenColumnTypeDO::getDbValue, queryRequest.getDbValue());
        IPage<GenColumnTypeDO> genColumnTypeIPage = genColumnTypeMapper.selectPage(PageTool.getPage(queryRequest), lambdaQueryWrapper);
        return genColumnTypeConvert.toPageResponse(genColumnTypeIPage);
    }

}
