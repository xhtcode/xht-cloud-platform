package com.xht.cloud.admin.module.user.factory;

import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.strategy.AbstractUserRouterStrategy;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.UserErrorStatusCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：用户信息获取工厂类
 *
 * @author : 小糊涂
 **/
public final class UserRouterFactory {

    /**
     * 用户策略存储器
     */
    private static final Map<Integer, AbstractUserRouterStrategy> USER_ROUTER_STRATEGY_MAP = new HashMap<>();

    /**
     * 注册策略执行器
     *
     * @param userTypeEnums {@link UserTypeEnums} 用户类型
     * @param strategy      策略执行器
     */
    public static void register(UserTypeEnums userTypeEnums, AbstractUserRouterStrategy strategy) {
        Assert.notNull(userTypeEnums, "用户类型获取失败");
        USER_ROUTER_STRATEGY_MAP.put(userTypeEnums.getValue(), strategy);
    }

    /**
     * 获取一个非空的策略执行器
     *
     * @param userTypeEnums {@link UserTypeEnums} 用户类型
     * @return 策略执行器
     */
    public static AbstractUserRouterStrategy getStrategyNoNull(UserTypeEnums userTypeEnums) {
        Assert.notNull(userTypeEnums, GlobalErrorStatusCode.PARAMS_ERROR);
        AbstractUserRouterStrategy strategy = USER_ROUTER_STRATEGY_MAP.get(userTypeEnums.getValue());
        Assert.notNull(strategy, UserErrorStatusCode.SYSTEM_ERROR);
        return strategy;
    }
}
