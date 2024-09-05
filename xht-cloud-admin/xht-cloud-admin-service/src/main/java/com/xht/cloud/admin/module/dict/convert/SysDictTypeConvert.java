package com.xht.cloud.admin.module.dict.convert;

import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeVo;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：字典类型
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDictTypeConvert extends IBaseConvert<SysDictTypeCreateRequest, SysDictTypeUpdateRequest, SysDictTypeResponse, SysDictTypeDO>, EntityWrapper<SysDictTypeDO> {

    /**
     * {@link SysDictTypeDO} to {@link SysDictTypeVo}
     */
    @Named(value = "DoToVo")
    SysDictTypeVo toVo(SysDictTypeDO testDO);



}
