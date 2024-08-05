package com.xht.cloud.framework.utils.spring;

import com.xht.cloud.framework.core.constant.SpringPropertiesNameConstant;

import java.util.Objects;

/**
 * 描述 ：服务相关工具类
 *
 * @author 小糊涂
 **/
public final class ServerUtils {


    /**
     * 判断服务名 是不是不相等
     *
     * @param applicationName 服务名称
     * @return true 不相等
     */
    public static boolean notThisServer(String applicationName) {
        return !Objects.equals(applicationName, SpringContextUtil.getProperty(SpringPropertiesNameConstant.SPRING_APPLICATION_KEY));
    }

}
