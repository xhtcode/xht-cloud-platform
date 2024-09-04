package com.xht.cloud.admin.module.dict.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDictItemConvert extends IBaseConvert<SysDictItemCreateRequest, SysDictItemUpdateRequest, SysDictItemQueryRequest, SysDictItemResponse, SysDictItemDO>, EntityWrapper<SysDictItemDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysDictItemDO> lambdaQuery(SysDictItemDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysDictItemDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysDictItemDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getDictId()), SysDictItemDO::getDictId, entity.getDictId())
                .like(StringUtils.hasText(entity.getDictCode()), SysDictItemDO::getDictCode, entity.getDictCode())
                .like(StringUtils.hasText(entity.getDictValue()), SysDictItemDO::getDictValue, entity.getDictValue())
                .eq(Objects.nonNull(entity.getDictStatus()), SysDictItemDO::getDictStatus, entity.getDictStatus())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysDictItemDO> lambdaUpdate(SysDictItemDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysDictItemDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysDictItemDO::getDictId, entity.getDictId())
                .set(SysDictItemDO::getDictCode, entity.getDictCode())
                .set(SysDictItemDO::getDictValue, entity.getDictValue())
                .set(SysDictItemDO::getDictSort, entity.getDictSort())
                .set(SysDictItemDO::getDictStatus, entity.getDictStatus())
                .set(SysDictItemDO::getDictDesc, entity.getDictDesc())
                ;
    }

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
