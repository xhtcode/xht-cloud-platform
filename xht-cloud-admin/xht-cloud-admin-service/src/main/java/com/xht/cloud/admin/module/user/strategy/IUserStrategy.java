package com.xht.cloud.admin.module.user.strategy;

import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import org.springframework.beans.factory.InitializingBean;

/**
 * 描述 ：用户策略判断注册
 *
 * @author : 小糊涂
 **/
public interface IUserStrategy extends InitializingBean {

    /**
     * 获取用户类型
     *
     * @return {@link UserTypeEnums} 用户类型
     */
    UserTypeEnums getUserType();
}
