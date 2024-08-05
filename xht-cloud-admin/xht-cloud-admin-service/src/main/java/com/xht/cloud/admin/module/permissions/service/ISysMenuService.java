package com.xht.cloud.admin.module.permissions.service;

import com.xht.cloud.admin.module.permissions.domain.request.SysMenuCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.framework.utils.treenode.INode;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 描述 ：菜单权限
 *
 * @author 小糊涂
 **/
public interface ISysMenuService {

    /**
     * 创建
     *
     * @param createRequest {@link SysMenuCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysMenuCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysMenuUpdateRequest}
     */
    void update(SysMenuUpdateRequest updateRequest);

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysMenuResponse}
     */
    SysMenuResponse findById(String id);

    /**
     * 条件查询全部(树)
     *
     * @param queryRequest {@link SysMenuQueryRequest}
     * @return {@link List<SysMenuResponse>} 菜单数据树
     */
    List<SysMenuResponse> list(SysMenuQueryRequest queryRequest);


    /**
     * 菜单参数校验并格式化数据
     *
     * @param addRequest {@link SysMenuCreateRequest}
     */
    SysMenuCreateRequest validationAndFormat(SysMenuCreateRequest addRequest);

    /**
     * 菜单参数校验并格式化数据
     *
     * @param id            {@link String} 数据库主键
     * @param updateRequest {@link SysMenuUpdateRequest}
     */
    default void validationAndFormat(String id, SysMenuUpdateRequest updateRequest) {
        BeanUtils.copyProperties(validationAndFormat(updateRequest), updateRequest);
        updateRequest.setId(id);
    }

    /**
     * 菜单 转换成树结构
     *
     * @param menus  {@link SysMenuResponse} 菜单
     * @param router {@link Boolean} true 时返回对象时router对象 false的时候是普通对象
     * @return 树结构
     */
    List<INode<String>> convert(List<SysMenuResponse> menus, boolean router);

    /**
     * 根据用户名还有菜单类型查询菜单
     *
     * @param userId        用户id
     * @param menuTypeEnums 菜单类型
     * @return {@link SysMenuResponse} 菜单数据
     */
    List<SysMenuResponse> selectByUserId(String userId, MenuTypeEnums[] menuTypeEnums);
}
