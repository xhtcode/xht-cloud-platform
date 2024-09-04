package com.xht.cloud.admin.module.sequence.convertor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceUpdateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysSequenceConvertor extends IBaseConvert<SysSequenceCreateRequest, SysSequenceUpdateRequest, SysSequenceQueryRequest, SysSequenceResponse, SysSequenceDO>, EntityWrapper<SysSequenceDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysSequenceDO> lambdaQuery(SysSequenceDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysSequenceDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper.like(StringUtils.hasText(entity.getSeqCode()), SysSequenceDO::getSeqCode, entity.getSeqCode()).like(StringUtils.hasText(entity.getSeqName()), SysSequenceDO::getSeqName, entity.getSeqName()).eq(Objects.nonNull(entity.getSeqLoop()), SysSequenceDO::getSeqLoop, entity.getSeqLoop()).eq(Objects.nonNull(entity.getResetFlag()), SysSequenceDO::getResetFlag, entity.getResetFlag());
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysSequenceDO> lambdaUpdate(SysSequenceDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysSequenceDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper.set(SysSequenceDO::getSeqCode, entity.getSeqCode()).set(SysSequenceDO::getSeqName, entity.getSeqName()).set(SysSequenceDO::getCurrentValue, entity.getCurrentValue()).set(SysSequenceDO::getStepValue, entity.getStepValue()).set(SysSequenceDO::getMaxValue, entity.getMaxValue()).set(SysSequenceDO::getMinValue, entity.getMinValue()).set(SysSequenceDO::getSeqLoop, entity.getSeqLoop()).set(SysSequenceDO::getResetFlag, entity.getResetFlag()).set(SysSequenceDO::getSeqFormat, entity.getSeqFormat()).set(SysSequenceDO::getSeqDesc, entity.getSeqDesc());
    }

}
