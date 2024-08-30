package com.xht.cloud.admin.module.user.strategy;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.module.user.factory.UserInfoFactory;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 描述 ：策略模式 用户信息获取
 *
 * @author : 小糊涂
 **/
public abstract class AbstractUserInfoStrategy<USER extends BaseDO> implements IUserStrategy {

    /**
     * 根据用户账号获取用户信息
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    public final UserCenterResponse getUserInfoByUserName(String userName) {
        Assert.hasText(userName, "用户账号不能为空");
        USER user = userName(userName);
        Assert.notNull(user, () -> new UsernameNotFoundException(userName));
        return builder(user);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    public final UserCenterResponse getUserInfoByUserId(String userId) {
        Assert.hasText(userId, "用户id不能为空");
        USER user = userId(userId);
        Assert.notNull(user, () -> new UsernameNotFoundException(userId));
        return builder(user);
    }

    /**
     * 根据用户账号获取数据库用户数据
     *
     * @param userName 用户账号
     * @return 数据库用户数据
     */
    protected abstract USER userName(String userName);

    /**
     * 根据用户id获取数据库用户数据
     *
     * @param userId 用户id
     * @return 数据库用户数据
     */
    protected abstract USER userId(String userId);

    /**
     * 用户构建
     *
     * @param userInfo 已从数据库查询出的用户信息
     * @return 用户信息
     */
    protected abstract UserCenterResponse builder(USER userInfo);

    /**
     * 初始化 策略工厂
     */
    @Override
    public final void afterPropertiesSet() {
        UserInfoFactory.register(getUserType(), this);
    }
}
