package com.xht.cloud.admin.module.dict.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeVo;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * 描述 ：字典
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDictTypeConvert extends IBaseConvert<SysDictTypeCreateRequest, SysDictTypeUpdateRequest, SysDictTypeQueryRequest, SysDictTypeResponse, SysDictTypeDO>, EntityWrapper<SysDictTypeDO> {

    /**
     * {@link SysDictTypeDO} to {@link SysDictTypeVo}
     */
    @Named(value = "DoToVo")
    SysDictTypeVo toVo(SysDictTypeDO testDO);


    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysDictTypeDO> lambdaQuery(SysDictTypeDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysDictTypeDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysDictTypeDO::getId, entity.getId())
                .like(StringUtils.hasText(entity.getDictType()), SysDictTypeDO::getDictType, entity.getDictType())
                .like(StringUtils.hasText(entity.getDictName()), SysDictTypeDO::getDictName, entity.getDictName())
                .eq(Objects.nonNull(entity.getDictStatus()), SysDictTypeDO::getDictStatus, entity.getDictStatus())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysDictTypeDO> lambdaUpdate(SysDictTypeDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysDictTypeDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysDictTypeDO::getDictType, entity.getDictType())
                .set(SysDictTypeDO::getDictName, entity.getDictName())
                .set(SysDictTypeDO::getDictStatus, entity.getDictStatus())
                .set(SysDictTypeDO::getDictDesc, entity.getDictDesc())
                .set(SysDictTypeDO::getDictSort, entity.getDictSort())
                ;
    }


}
