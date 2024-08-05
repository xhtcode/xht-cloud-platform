package com.xht.cloud.admin.module.area.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoCreateRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.domain.response.SysAreaInfoResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysAreaInfoConvert extends IBaseConvert<SysAreaInfoCreateRequest, SysAreaInfoUpdateRequest, SysAreaInfoQueryRequest, SysAreaInfoResponse, SysAreaInfoDO>, EntityWrapper<SysAreaInfoDO> {

    /**
     * {@link SysAreaInfoDO} to {@link SysAreaInfoResponse}
     */
    @Named(value = "DoToResponse")
    @Mapping(target = "leaf", expression = "java(com.xht.cloud.admin.module.area.convert.AreaLeafConvert.convert(testDO.getLeaf()))")
    SysAreaInfoResponse toResponse(SysAreaInfoDO testDO);


    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<SysAreaInfoDO> lambdaQuery(SysAreaInfoDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysAreaInfoDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysAreaInfoDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getParentId()), SysAreaInfoDO::getParentId, entity.getParentId())
                .like(StringUtils.hasText(entity.getName()), SysAreaInfoDO::getName, entity.getName())
                .eq(StringUtils.hasText(entity.getLevel()), SysAreaInfoDO::getLevel, entity.getLevel())
                .like(StringUtils.hasText(entity.getAreaNo()), SysAreaInfoDO::getAreaNo, entity.getAreaNo())
                .eq(StringUtils.hasText(entity.getCategory()), SysAreaInfoDO::getCategory, entity.getCategory())
                .eq(StringUtils.hasText(entity.getMsg()), SysAreaInfoDO::getMsg, entity.getMsg())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysAreaInfoDO> lambdaUpdate(SysAreaInfoDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysAreaInfoDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysAreaInfoDO::getParentId, entity.getParentId())
                .set(SysAreaInfoDO::getName, entity.getName())
                .set(SysAreaInfoDO::getLevel, entity.getLevel())
                .set(SysAreaInfoDO::getAreaNo, entity.getAreaNo())
                .set(SysAreaInfoDO::getCategory, entity.getCategory())
                .set(SysAreaInfoDO::getMsg, entity.getMsg())
                ;
    }
}
