package com.xht.cloud.admin.module.sequence.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.mapper.SysSequenceMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 描述 ：序列
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysSequenceDao extends BaseDaoImpl<SysSequenceMapper, SysSequenceDO> {

    /**
     * 判断序列编码是否存在
     *
     * @param seqCode 序列编码
     * @return 存在true
     */
    public boolean existsSeqCode(String seqCode) {
        return exists(lambdaQuery().eq(SysSequenceDO::getSeqCode, seqCode));
    }

    /**
     * 判断序列编码是否存在但是不包括指定的id
     *
     * @param id      id
     * @param seqCode 序列编码
     * @return 存在true
     */
    public boolean existsSeqCodeNoId(String id, String seqCode) {
        return exists(lambdaQuery().ne(SysSequenceDO::getId, id).eq(SysSequenceDO::getSeqCode, seqCode));
    }

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<SysSequenceDO> pageQueryRequest(SysSequenceQueryRequest queryRequest) {
        LambdaQueryWrapper<SysSequenceDO> wrapper = new LambdaQueryWrapper<>();
         wrapper
                 .like(StringUtils.hasText(queryRequest.getSeqCode()), SysSequenceDO::getSeqCode, queryRequest.getSeqCode())
                 .like(StringUtils.hasText(queryRequest.getSeqName()), SysSequenceDO::getSeqName, queryRequest.getSeqName())
                 .eq(Objects.nonNull(queryRequest.getSeqLoop()), SysSequenceDO::getSeqLoop, queryRequest.getSeqLoop())
                 .eq(Objects.nonNull(queryRequest.getResetFlag()), SysSequenceDO::getResetFlag, queryRequest.getResetFlag());
        return page(PageTool.getPage(queryRequest),wrapper);
    }
}
