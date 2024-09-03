package com.xht.cloud.admin.constant;

/**
 * 描述 ：用户常量
 *
 * @author : 小糊涂
 **/
public interface UserConstant {

    /**
     * 默认密码
     */
    String DEFAULT_STAFF_USER_PASS_WORD = "123456";

    /**
     * 修改密码权限code
     */
    String UPDATE_USER_STATUS = "update:user:password";

    /**
     * 修改部门权限code
     */
    String UPDATE_USER_DEPT_ID = "update:user:dept";
}
