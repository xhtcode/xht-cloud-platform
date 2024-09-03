package com.xht.cloud.admin.module.log.dao;

import com.xht.cloud.admin.module.log.domain.dataobject.SysLoginLogDO;
import com.xht.cloud.admin.module.log.mapper.SysLoginLogMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：登录日志
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysLoginLogDao extends BaseDaoImpl<SysLoginLogMapper, SysLoginLogDO> {

}
