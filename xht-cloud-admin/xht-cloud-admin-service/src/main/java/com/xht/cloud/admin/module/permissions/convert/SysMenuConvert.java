package com.xht.cloud.admin.module.permissions.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * 描述 ：菜单权限
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysMenuConvert extends IBaseConvert<SysMenuCreateRequest, SysMenuUpdateRequest, SysMenuQueryRequest, SysMenuResponse, SysMenuDO>, EntityWrapper<SysMenuDO> {

    /**
     * {@link SysMenuDO} to {@link SysMenuCreateRequest}
     */
    SysMenuCreateRequest toRequest(SysMenuDO menuRequest);

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysMenuDO> lambdaQuery(SysMenuDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysMenuDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getParentId()), SysMenuDO::getParentId, entity.getParentId())
                .eq(StringUtils.hasText(entity.getMenuType()), SysMenuDO::getMenuType, entity.getMenuType())
                .like(StringUtils.hasText(entity.getMenuName()), SysMenuDO::getMenuName, entity.getMenuName())
                .like(StringUtils.hasText(entity.getMenuPath()), SysMenuDO::getMenuPath, entity.getMenuPath())
                .eq(StringUtils.hasText(entity.getMenuStatus()), SysMenuDO::getMenuStatus, entity.getMenuStatus())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysMenuDO> lambdaUpdate(SysMenuDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysMenuDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysMenuDO::getParentId, entity.getParentId())
                .set(SysMenuDO::getMenuType, entity.getMenuType())
                .set(SysMenuDO::getMenuName, entity.getMenuName())
                .set(SysMenuDO::getMenuPath, entity.getMenuPath())
                .set(SysMenuDO::getMenuViewName, entity.getMenuViewName())
                .set(SysMenuDO::getMenuViewPath, entity.getMenuViewPath())
                .set(SysMenuDO::getMenuIcon, entity.getMenuIcon())
                .set(SysMenuDO::getMenuRedirect, entity.getMenuRedirect())
                .set(SysMenuDO::getMenuActive, entity.getMenuActive())
                .set(SysMenuDO::getMenuAuthority, entity.getMenuAuthority())
                .set(SysMenuDO::getMenuHidden, entity.getMenuHidden())
                .set(SysMenuDO::getMenuStatus, entity.getMenuStatus())
                .set(SysMenuDO::getMenuLink, entity.getMenuLink())
                .set(SysMenuDO::getMenuCache, entity.getMenuCache())
                .set(SysMenuDO::getMenuAffix, entity.getMenuAffix())
                .set(SysMenuDO::getMenuSort, entity.getMenuSort())
                ;
    }

}
