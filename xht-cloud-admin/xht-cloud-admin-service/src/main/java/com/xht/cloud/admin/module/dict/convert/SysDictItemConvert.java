package com.xht.cloud.admin.module.dict.convert;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDictItemConvert extends IBaseConvert<SysDictItemCreateRequest, SysDictItemResponse, SysDictItemDO> {

    @Named(value = "DoToDTO")
    SysDictItemDTO toDTO(SysDictItemDO dictItemDO);

    default List<SysDictItemDTO> toDTO(List<SysDictItemDO> dictItemDOS) {
        if (CollectionUtils.isEmpty(dictItemDOS)) return Collections.emptyList();
        List<SysDictItemDTO> list = new ArrayList<>(dictItemDOS.size());
        for (SysDictItemDO item : dictItemDOS) {
            list.add(toDTO(item));
        }
        return list;
    }
}
