package com.xht.cloud.admin.module.sequence.service.impl;

import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.convertor.SysSequenceConvertor;
import com.xht.cloud.admin.module.sequence.dao.SysSequenceDao;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceUpdateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.admin.module.sequence.service.ISysSequenceService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
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

    private final SysSequenceDao sysSequenceDao;

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
        if (sysSequenceDao.existsSeqCode(createRequest.getSeqCode())) {
            throw new SequenceException(String.format("序列编码`%s`不能重复", createRequest.getSeqCode()));
        }
        SysSequenceDO entity = sysSequenceConvertor.toDO(createRequest);
        sysSequenceDao.save(entity);
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
        Assert.notNull(updateRequest, "序列修改信息不能为空");
        Assert.notNull(updateRequest.getId(), "序列修改信息id不能为空");
        if (sysSequenceDao.existsSeqCodeNoId(updateRequest.getId(), updateRequest.getSeqCode())) {
            throw new SequenceException(String.format("序列编码`%s`不能重复", updateRequest.getSeqCode()));
        }
        sysSequenceDao.updateRequest(updateRequest);
    }


    /**
     * 删除序列
     *
     * @param ids {@link String} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        sysSequenceDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询序列详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysSequenceResponse}
     */
    @Override
    public SysSequenceResponse findById(String id) {
        return sysSequenceConvertor.toResponse(sysSequenceDao.getById(id));
    }

    /**
     * 分页查询序列
     *
     * @param queryRequest {@link SysSequenceQueryRequest}
     * @return {@link SysSequenceResponse} 分页详情
     */
    @Override
    public PageResponse<SysSequenceResponse> findPage(SysSequenceQueryRequest queryRequest) {
        return sysSequenceConvertor.toPageResponse(sysSequenceDao.pageQueryRequest(queryRequest));
    }

}
