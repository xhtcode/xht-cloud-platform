package com.xht.cloud.admin.module.user.factory;

import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.strategy.AbstractUserUpdateStrategy;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.UserErrorStatusCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ： 用户修改获取工厂类
 *
 * @author : 小糊涂
 **/
public final class UserUpdateFactory {

    /**
     * 用户策略存储器
     */
    private static final Map<Integer, AbstractUserUpdateStrategy> USER_UPDATE_STRATEGY_MAP = new HashMap<>();


    /**
     * 注册策略执行器
     *
     * @param userTypeEnums {@link UserTypeEnums} 用户类型
     * @param strategy      策略执行器
     */
    public static void register(UserTypeEnums userTypeEnums, AbstractUserUpdateStrategy strategy) {
        Assert.notNull(userTypeEnums, "用户类型获取失败");
        USER_UPDATE_STRATEGY_MAP.put(userTypeEnums.getValue(), strategy);
    }

    /**
     * 获取一个非空的策略执行器
     *
     * @param userTypeEnums {@link UserTypeEnums} 用户类型
     * @return 策略执行器
     */
    public static AbstractUserUpdateStrategy getStrategyNoNull(UserTypeEnums userTypeEnums) {
        Assert.notNull(userTypeEnums, GlobalErrorStatusCode.PARAMS_ERROR);
        AbstractUserUpdateStrategy strategy = USER_UPDATE_STRATEGY_MAP.get(userTypeEnums.getValue());
        Assert.notNull(strategy, UserErrorStatusCode.SYSTEM_ERROR);
        return strategy;
    }

}
