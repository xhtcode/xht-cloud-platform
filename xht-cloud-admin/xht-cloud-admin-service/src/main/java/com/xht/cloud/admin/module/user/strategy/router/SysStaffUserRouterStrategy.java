package com.xht.cloud.admin.module.user.strategy.router;

import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.permissions.dao.SysMenuDao;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.strategy.AbstractUserRouterStrategy;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.user.UserNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述 ：工作人员用户路由获取
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SysStaffUserRouterStrategy extends AbstractUserRouterStrategy {

    private final SysMenuDao sysMenuDao;

    private final SysUserStaffDao userStaffDao;

    /**
     * 根据用户账号获取菜单信息
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    @Override
    protected List<SysMenuDO> getMenuList(String userName) {
        String userId = userStaffDao.getUserIdByUserName(userName);
        Assert.hasText(userId, UserNotFountException::new);
        return sysMenuDao.selectMenuRouter(userId);
    }

    /**
     * 获取用户类型
     */
    @Override
    public UserTypeEnums getUserType() {
        return UserTypeEnums.STAFF;
    }
}
