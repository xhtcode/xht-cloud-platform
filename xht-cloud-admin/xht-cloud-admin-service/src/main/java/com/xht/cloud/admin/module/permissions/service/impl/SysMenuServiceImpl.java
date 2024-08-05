package com.xht.cloud.admin.module.permissions.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.constant.MenuConstant;
import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.admin.exceptions.MenuException;
import com.xht.cloud.admin.module.permissions.convert.SysMenuConvert;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleMenuDO;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuCreateRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuQueryRequest;
import com.xht.cloud.admin.module.permissions.domain.request.SysMenuUpdateRequest;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.admin.module.permissions.mapper.SysMenuMapper;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMenuMapper;
import com.xht.cloud.admin.module.permissions.service.ISysMenuService;
import com.xht.cloud.admin.module.user.domain.response.MetaVo;
import com.xht.cloud.admin.module.user.domain.response.RouterVo;
import com.xht.cloud.admin.tool.ExceptionTool;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import com.xht.cloud.framework.utils.treenode.TreeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述 ：菜单管理
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements ISysMenuService {

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    private final SysMenuConvert sysMenuConvert;

    /**
     * 创建菜单
     *
     * @param createRequest {@link SysMenuCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysMenuCreateRequest createRequest) {
        SysMenuDO entity = sysMenuConvert.toDO(createRequest);
        sysMenuMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改菜单
     *
     * @param updateRequest SysMenuUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "菜单修改信息不能为空");
        Assert.hasText(updateRequest.getPkId(), "菜单修改信息id不能为空");
        //当状态改为禁用那么他的所有下级都是禁用
        if (Objects.equals(MenuConstant.STATUS_ERROR, updateRequest.getMenuStatus())) {
            List<String> menuIds = sysMenuMapper
                    .findChildByMenuIdAndMenuStatus(updateRequest.getId(), MenuConstant.STATUS_SUCCESS)
                    .stream()
                    .map(SysMenuDO::getId)
                    .filter(id -> !Objects.equals(updateRequest.getId(), id))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(menuIds)){
                sysMenuMapper.update(
                        sysMenuConvert.lambdaUpdate()
                                .set(SysMenuDO::getMenuStatus, MenuConstant.STATUS_ERROR)
                                .in(SysMenuDO::getId, menuIds)
                );
            }
        }
        sysMenuMapper.updateById(sysMenuConvert.toDO(updateRequest));
        // @formatter:on
    }


    /**
     * 删除菜单
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        ExceptionTool.menuValidation(sysMenuMapper.selectCountIn(SysMenuDO::getParentId, ids) > 0, "该菜单有子菜单，禁止删除");
        ExceptionTool.menuValidation(sysRoleMenuMapper.selectCountIn(SysRoleMenuDO::getMenuId, ids) > 0, "该菜单已绑定菜单，禁止删除");
        LambdaQueryWrapper<SysMenuDO> lambdaQueryWrapper = sysMenuConvert.lambdaQuery()
                .select(
                        SysMenuDO::getId,
                        SysMenuDO::getParentId,
                        SysMenuDO::getMenuType,
                        SysMenuDO::getMenuStatus
                )
                .in(SysMenuDO::getId, ids);
        List<SysMenuDO> sysMenuDOS = sysMenuMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(sysMenuDOS) || sysMenuDOS.size() != ids.size()) {
            throw new MenuException("参数错误，查询不到菜单!");
        }
        sysMenuMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询菜单详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysMenuResponse}
     */
    @Override
    public SysMenuResponse findById(String id) {
        return sysMenuConvert.toResponse(sysMenuMapper.findById(id).orElse(null));
    }

    /**
     * 条件查询全部(菜单树)
     *
     * @param queryRequest {@link SysMenuQueryRequest}
     * @return {@link List<SysMenuResponse>} 菜单数据树
     */
    @Override
    public List<SysMenuResponse> list(SysMenuQueryRequest queryRequest) {
        LambdaQueryWrapper<SysMenuDO> lambdaQueryWrapper = sysMenuConvert.lambdaQuery(sysMenuConvert.toDO(queryRequest)).notIn(!CollectionUtils.isEmpty(queryRequest.getNotMenuType()), SysMenuDO::getMenuType, queryRequest.getNotMenuType())
                .orderByAsc(SysMenuDO::getMenuSort);
        return sysMenuConvert.toResponse(sysMenuMapper.selectList(lambdaQueryWrapper));
    }

    /**
     * 菜单参数校验并格式化数据
     *
     * @param menuRequest {@link SysMenuCreateRequest}
     */
    @Override
    public SysMenuCreateRequest validationAndFormat(SysMenuCreateRequest menuRequest) {
        // @formatter:off
        String id = null;
        if (menuRequest instanceof SysMenuUpdateRequest) {
            id = ((SysMenuUpdateRequest) menuRequest).getId();
        }
        Assert.notNull(menuRequest, "菜单参数校验参数失败");
        SysMenuDO parentMenu;
        if (Objects.equals(MenuConstant.TREE_DEFAULT, menuRequest.getParentId())) {
            parentMenu = new SysMenuDO();
            parentMenu.setMenuType(MenuTypeEnums.M.getValue());
        } else {
            parentMenu = sysMenuMapper.selectById(menuRequest.getParentId());
        }
        ExceptionTool.menuValidation(Objects.isNull(parentMenu), "查询不到上级菜单!");
        ExceptionTool.menuValidation(MenuTypeEnums.B.getValue().equals(parentMenu.getMenuType()), "上级菜单是按钮!");

        String menuPath = menuRequest.getMenuPath();
        if (StringUtils.hasText(menuPath)) {
            long menuPathCount = sysMenuMapper.selectCount(SysMenuDO::getMenuPath, menuPath);
            ExceptionTool.menuValidation(menuPathCount > 1, "菜单路由地址重复!");
        }
        long menuViewNameCount = sysMenuMapper.selectCount(
                sysMenuConvert.lambdaQuery()
                        .eq(SysMenuDO::getMenuViewName, menuRequest.getMenuViewName())
                        .eq(SysMenuDO::getMenuLink,"0")
                        .ne(StringUtils.hasText(id), SysMenuDO::getId, id)
        );
        ExceptionTool.menuValidation(menuViewNameCount > 0, "组件视图名称重复!");
        SysMenuDO result = new SysMenuDO();
        result.setParentId(menuRequest.getParentId());
        result.setMenuType(menuRequest.getMenuType());
        result.setMenuStatus(menuRequest.getMenuStatus());
        result.setMenuName(menuRequest.getMenuName());
        result.setMenuSort(menuRequest.getMenuSort());
        switch (Objects.requireNonNull(MenuTypeEnums.ofValue(menuRequest.getMenuType()))) {
            case M -> { // 目录
                ExceptionTool.menuValidation(MenuTypeEnums.C.getValue().equals(parentMenu.getMenuType()), "目录上级不能是菜单！");
                result.setMenuIcon(menuRequest.getMenuIcon());
                result.setMenuPath(menuRequest.getMenuPath());
                result.setMenuRedirect(menuRequest.getMenuRedirect());
            }
            case C -> { // 菜单
                ExceptionTool.menuValidation(MenuTypeEnums.C.getValue().equals(parentMenu.getMenuType()), "菜单上级不能是菜单！");
                result.setMenuIcon(menuRequest.getMenuIcon());
                result.setMenuViewName(menuRequest.getMenuViewName());
                result.setMenuViewPath(menuRequest.getMenuViewPath());
                result.setMenuPath(menuRequest.getMenuPath());
                result.setMenuActive(StringUtils.emptyToDefault(menuRequest.getMenuActive(), menuRequest.getMenuPath()));
                result.setMenuHidden(menuRequest.getMenuHidden());
                result.setMenuCache(menuRequest.getMenuCache());
                result.setMenuLink(menuRequest.getMenuLink());
                result.setMenuAffix(menuRequest.getMenuAffix());
            }
            case B -> // 按钮
                result.setMenuAuthority(result.getMenuAuthority());
            default -> {
                ExceptionTool.menuValidation(MenuTypeEnums.M.getValue().equals(parentMenu.getMenuType()), "按钮上级不能是目录！");
                throw new MenuException("菜单类型不合法!");
            }
        }
        // @formatter:on
        return sysMenuConvert.toRequest(result);
    }

    /**
     * 菜单 转换成树结构
     *
     * @param menus  {@link SysMenuDO} 菜单
     * @param router {@link Boolean} true 时返回对象时router对象 false的时候是普通对象
     * @return 树结构
     */
    @Override
    public List<INode<String>> convert(List<SysMenuResponse> menus, boolean router) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<INode<String>> result = new ArrayList<>(menus.size());
        for (SysMenuResponse menu : menus) {
            if (router) {
                //路由对象
                String perms = menu.getMenuAuthority();
                RouterVo routerVo = new RouterVo();
                routerVo.setName(StringUtils.emptyToDefault(menu.getMenuViewName(), menu.getId()));
                routerVo.setPath(menu.getMenuPath());
                routerVo.setRedirect(menu.getMenuRedirect());
                routerVo.setComponent(String.format("..%s", menu.getMenuViewPath()));
                MetaVo metaVo = new MetaVo();
                metaVo.setTitle(menu.getMenuName());
                metaVo.setIcon(menu.getMenuIcon());
                metaVo.setMenuType(menu.getMenuType());
                metaVo.setActiveMenu(menu.getMenuActive());
                metaVo.setLinkStatus(Objects.equals(MenuConstant.STATUS_SUCCESS, menu.getMenuLink()));
                metaVo.setHiddenStatus(Objects.equals(MenuConstant.STATUS_ERROR, menu.getMenuHidden()));
                metaVo.setAffixStatus(Objects.equals(MenuConstant.STATUS_SUCCESS, menu.getMenuAffix()));
                metaVo.setKeepAliveStatus(Objects.equals(MenuConstant.STATUS_SUCCESS, menu.getMenuCache()));
                metaVo.setRoles(StrUtil.splitTrim(perms, ","));//perms
                metaVo.setRank(menu.getMenuSort());
                routerVo.setMeta(metaVo);
                result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(BeanUtil.beanToMap(routerVo)));
            } else {
                //菜单对象
                result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(BeanUtil.beanToMap(menu)));
            }
        }
        return TreeUtils.buildList(result);
    }

    /**
     * 根据用户名还有菜单类型查询菜单
     *
     * @param userId        用户id
     * @param menuTypeEnums 菜单类型
     * @return {@link SysMenuResponse} 菜单数据
     */
    @Override
    public List<SysMenuResponse> selectByUserId(String userId, MenuTypeEnums[] menuTypeEnums) {
        Assert.notNull(userId, "[菜单管理] userId 不能为空");
        if (Objects.isNull(menuTypeEnums)) {
            menuTypeEnums = MenuTypeEnums.values();
        }
        List<SysMenuDO> sysMenuDOS;
        if (SecurityContextUtil.isAdmin()) {
            sysMenuDOS = sysMenuMapper.selectListIn(SysMenuDO::getMenuType, Arrays.stream(menuTypeEnums).map(MenuTypeEnums::getValue).toList());
        } else {
            sysMenuDOS = sysMenuMapper.selectByUserIdAndMenuType(userId, Arrays.stream(menuTypeEnums).map(MenuTypeEnums::getValue).toList());
        }
        return sysMenuConvert.toResponse(sysMenuDOS);
    }

}
