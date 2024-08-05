package com.xht.cloud.admin.module.area.mapper;

import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Mapper
public interface SysAreaInfoMapper extends BaseMapperX<SysAreaInfoDO> {

    List<SysAreaInfoDO> selectListByRequest(@Param("query") SysAreaInfoQueryRequest queryRequest);
}
