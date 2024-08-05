package com.xht.cloud.admin.module.permissions.mapper;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述 ：菜单权限表
 *
 * @author 小糊涂
 **/
@Mapper
public interface SysMenuMapper extends BaseMapperX<SysMenuDO> {

    /**
     * 根据菜单id查询出所有的下级的包括自己
     *
     * @param menuId     菜单id
     * @param menuStatus 菜单状态
     * @return {@link SysMenuDO }
     */
    List<SysMenuDO> findChildByMenuIdAndMenuStatus(@Param("menuId") String menuId, @Param("menuStatus") String menuStatus);

    List<SysMenuDO> selectByUserIdAndMenuType(@Param("userId") String userId, @Param("menuTypes") List<String> menuTypeEnums);
}
