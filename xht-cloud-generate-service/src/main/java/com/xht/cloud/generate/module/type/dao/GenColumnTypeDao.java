package com.xht.cloud.generate.module.type.dao;

import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import com.xht.cloud.generate.module.type.mapper.GenColumnTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenColumnTypeDao extends BaseDaoImpl<GenColumnTypeMapper, GenColumnTypeDO> {
}
