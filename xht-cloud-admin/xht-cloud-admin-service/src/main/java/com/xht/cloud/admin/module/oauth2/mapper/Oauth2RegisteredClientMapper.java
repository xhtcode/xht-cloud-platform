package com.xht.cloud.admin.module.oauth2.mapper;

import com.xht.cloud.admin.module.oauth2.domain.dataobject.Oauth2RegisteredClientDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Mapper
public interface Oauth2RegisteredClientMapper extends BaseMapperX<Oauth2RegisteredClientDO> {

}
