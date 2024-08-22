package com.xht.cloud.admin.module.sequence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.convertor.SysSequenceConvertor;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceUpdateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.admin.module.sequence.mapper.SysSequenceMapper;
import com.xht.cloud.admin.module.sequence.service.ISysSequenceService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSequenceServiceImpl implements ISysSequenceService {

    private final SysSequenceMapper sysSequenceMapper;

    private final SysSequenceConvertor sysSequenceConvertor;

    /**
     * 创建序列
     *
     * @param createRequest {@link SysSequenceCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysSequenceCreateRequest createRequest) {
        Assert.notNull(createRequest, "序列添加信息不能为空");
        long sysSequenceCount = sysSequenceMapper.selectCount(SysSequenceDO::getSeqCode, createRequest.getSeqCode());
        if (sysSequenceCount > 0) {
            throw new SequenceException(String.format("序列编码`%s`不能重复", createRequest.getSeqCode()));
        }
        SysSequenceDO entity = sysSequenceConvertor.toDO(createRequest);
        sysSequenceMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改序列
     *
     * @param updateRequest SysSequenceUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysSequenceUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "序列修改信息不能为空");
        Assert.notNull(updateRequest.getPkId(), "序列修改信息id不能为空");
        LambdaQueryWrapper<SysSequenceDO> seqCodeQuery = sysSequenceConvertor.lambdaQuery()
                .eq(SysSequenceDO::getSeqCode, updateRequest.getSeqCode())
                .ne(SysSequenceDO::getId, updateRequest.getPkId());
        Long l = sysSequenceMapper.selectCount(seqCodeQuery);
        if (l > 0) {
            throw new SequenceException(String.format("序列编码`%s`不能重复", updateRequest.getSeqCode()));
        }
        sysSequenceMapper.updateById(sysSequenceConvertor.toDO(updateRequest));
        // @formatter:on
    }


    /**
     * 删除序列
     *
     * @param ids {@link List <String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        // @formatter:off
        sysSequenceMapper.deleteBatchIds(ids);
        // @formatter:on
    }

    /**
     * 根据id查询序列详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysSequenceResponse}
     */
    @Override
    public SysSequenceResponse findById(String id) {
        return sysSequenceConvertor.toResponse(sysSequenceMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询序列
     *
     * @param queryRequest {@link SysSequenceQueryRequest}
     * @return {@link SysSequenceResponse} 分页详情
     */
    @Override
    public PageResponse<SysSequenceResponse> findPage(SysSequenceQueryRequest queryRequest) {
        IPage<SysSequenceDO> sysConfigIPage = sysSequenceMapper.selectPage(PageTool.getPage(queryRequest), sysSequenceConvertor.lambdaQuery(sysSequenceConvertor.toDO(queryRequest)));
        return sysSequenceConvertor.toPageResponse(sysConfigIPage);
    }

}
