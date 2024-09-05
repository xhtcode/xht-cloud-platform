package com.xht.cloud.admin.module.sequence.generate.sequence;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.enums.GenerateIdType;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.dao.SysSequenceDao;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.IdRequest;
import com.xht.cloud.admin.module.sequence.generate.GenerateIdHandler;
import com.xht.cloud.admin.tool.SequenceFormat;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述 ：自定义序列生成器
 *
 * @author 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateSequenceHandler extends GenerateIdHandler {

    private final SysSequenceDao sysSequenceDao;

    /**
     * 生成id
     *
     * @param request {@link IdRequest}
     * @return id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generate(IdRequest request) {
        Assert.isTrue(!StringUtils.hasText(request.getSeqCode()), () -> new SequenceException("序列code不能为空!"));
        SysSequenceDO sysSequenceDO = sysSequenceDao.selectOne(SysSequenceDO::getSeqCode, request.getSeqCode()).orElseThrow(() -> new SequenceException("查询不到序列!"));
        String format = SequenceFormat.format(sysSequenceDO);
        sysSequenceDao.update(new LambdaUpdateWrapper<SysSequenceDO>()
                    .set(SysSequenceDO::getCurrentValue, sysSequenceDO.getCurrentValue())
                    .eq(SysSequenceDO::getId, sysSequenceDO.getId())
            );
        System.out.println(Thread.currentThread().getName() + "\t" + format);
        return format;
    }

    /**
     * 生成id
     *
     * @return {@link GenerateIdType}
     */
    @Override
    protected GenerateIdType getType() {
        return GenerateIdType.SEQUENCE;
    }
}
