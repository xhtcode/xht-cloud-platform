package com.xht.cloud.framework.web.constant;

/**
 * 描述 ：接口签名切面常量
 *
 * @author 小糊涂
 **/
public interface ApiSignatureConstant {
    /**
     * 应用的唯一标识
     */
    String APP_ID = "app-id";

    /**
     * 公匙（相当于账号）
     */
    String APP_KEY = "app-key";

    /**
     * 签名字段sign
     */
    String SIGN = "sign";

    /**
     * 临时流水号nonce，至少为10位 ，有效期内防重复提交
     */
    String NONCE = "nonce";

    /**
     * 加入timeStamp（时间戳），以服务端当前时间为准，单位为ms ，5分钟内数据有效
     */
    String TIMESTAMP = "timestamp";


}
