package com.xht.cloud.admin.module.log.dao;

import com.xht.cloud.admin.module.log.domain.dataobject.SysOperationLogDO;
import com.xht.cloud.admin.module.log.mapper.SysOperationLogMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：操作日志
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysOperationLogDao extends BaseDaoImpl<SysOperationLogMapper, SysOperationLogDO> {

}
