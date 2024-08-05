package com.xht.cloud.generate.module.config.mapper;

import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Mapper
public interface GenCodeConfigMapper extends BaseMapperX<GenCodeConfigDO> {

}
