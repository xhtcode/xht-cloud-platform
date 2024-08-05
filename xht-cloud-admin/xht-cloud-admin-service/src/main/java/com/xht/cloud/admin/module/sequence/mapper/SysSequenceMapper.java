package com.xht.cloud.admin.module.sequence.mapper;

import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Mapper
public interface SysSequenceMapper extends BaseMapperX<SysSequenceDO> {
}
