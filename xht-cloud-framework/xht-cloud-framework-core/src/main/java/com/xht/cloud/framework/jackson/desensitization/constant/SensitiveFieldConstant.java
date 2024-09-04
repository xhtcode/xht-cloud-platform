package com.xht.cloud.framework.jackson.desensitization.constant;

/**
 * 描述 ： 自定义json序列化
 *
 * @author 小糊涂
 **/
public interface SensitiveFieldConstant {

    /**
     * 所有字段
     */
    String SKIP_ALL_FIELDS = "*";

    /**
     * 手机号
     */
    String PHONE = "PHONE";

    /**
     * 固定电话
     */
    String FIXED_PHONE = "FIXED_PHONE";

    /**
     * 邮箱
     */
    String EMAIL = "EMAIL";

    /**
     * 身份证号
     */
    String ID_CARD = "ID_CARD";

    /**
     * 地址
     */
    String ADDRESS = "ADDRESS";

    /**
     * 银行卡号
     */
    String BANK_CARD = "BANK_CARD";

    /**
     * 中文姓名脱敏
     */
    String NAME = "NAME";

    /**
     * 密码脱敏
     */
    String PASSWORD = "PASSWORD";


}



