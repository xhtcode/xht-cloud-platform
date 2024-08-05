package com.xht.cloud.framework.core.developer;

import java.util.Objects;


/**
 * 描述 ：开发工具类
 *
 * @author 小糊涂
 **/
public final class DeveloperTool {


    /**
     * 判断当前是不是开发环境
     *
     * @return true是 false不是
     */
    public static boolean isDevActiveProfile(String activeProfile) {
        return Objects.equals(DeveloperConstant.DEV_ACTIVE_PROFILE, activeProfile);
    }

    /**
     * 判断当前是不是测试环境
     *
     * @return true是 false不是
     */
    public static boolean isTestActiveProfile(String activeProfile) {
        return Objects.equals(DeveloperConstant.TEST_ACTIVE_PROFILE, activeProfile);
    }

    /**
     * 判断当前是不是生产环境
     *
     * @return true是 false不是
     */
    public static boolean isProdActiveProfile(String activeProfile) {
        return Objects.equals(DeveloperConstant.PROD_ACTIVE_PROFILE, activeProfile);
    }

    /**
     * 判断是不是系统变量
     *
     * @param systemStatus {@link SuperSystemStatus}
     * @return true 是
     */
    public static boolean isSystemStatus(SuperSystemStatus systemStatus) {
        return Objects.equals(systemStatus, SuperSystemStatus.YES);
    }

    /**
     * 判断是不是系统变量
     *
     * @param systemStatusValue {@link SuperSystemStatus#getValue()}
     * @return true 是
     */
    public static boolean isSystemStatus(Integer systemStatusValue) {
        return Objects.equals(SuperSystemStatus.YES.getValue(), systemStatusValue);
    }
}
