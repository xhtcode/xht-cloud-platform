package com.xht.cloud.admin.module.permissions.mapper;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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

    /**
     * 根据用户名还有菜单类型查询菜单
     *
     * @param userId        用户id
     * @param list 菜单类型
     * @return {@link SysMenuResponse} 菜单数据
     */
    List<SysMenuDO> selectByUserIdAndMenuType(@Param("userId") String userId, @Param("menuTypes") List<String> menuTypeEnums);

    Set<String> selectMenuAuthorityByUserId(@Param("userId") Serializable userId);
}
