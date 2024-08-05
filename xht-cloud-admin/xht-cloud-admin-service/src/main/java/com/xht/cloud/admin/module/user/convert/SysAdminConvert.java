package com.xht.cloud.admin.module.user.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.user.domain.dataobject.SysAdminDO;
import com.xht.cloud.admin.module.user.domain.request.SysAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysAdminResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysAdminConvert extends IBaseConvert<SysAdminCreateRequest, SysAdminUpdateRequest, SysAdminQueryRequest, SysAdminResponse, SysAdminDO>, EntityWrapper<SysAdminDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysAdminDO> lambdaQuery(SysAdminDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysAdminDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .like(StringUtils.hasText(entity.getUserName()), SysAdminDO::getUserName, entity.getUserName())
                .like(StringUtils.hasText(entity.getContactPhone()), SysAdminDO::getContactPhone, entity.getContactPhone())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysAdminDO> lambdaUpdate(SysAdminDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        return new LambdaUpdateWrapper<SysAdminDO>()
                .like(StringUtils.hasText(entity.getUserName()), SysAdminDO::getUserName, entity.getUserName())
                .like(StringUtils.hasText(entity.getUserAvatar()), SysAdminDO::getUserAvatar, entity.getUserAvatar())
                .like(StringUtils.hasText(entity.getContactPhone()), SysAdminDO::getContactPhone, entity.getContactPhone())
                ;
    }
}
