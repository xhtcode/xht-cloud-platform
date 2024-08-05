package com.xht.cloud.admin.module.log.mapper;

import com.xht.cloud.admin.module.log.domain.dataobject.SysLoginLogDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Mapper
public interface SysLoginLogMapper extends BaseMapperX<SysLoginLogDO> {
}
