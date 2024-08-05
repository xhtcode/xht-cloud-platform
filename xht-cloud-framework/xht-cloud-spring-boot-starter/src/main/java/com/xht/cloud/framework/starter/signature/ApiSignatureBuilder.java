package com.xht.cloud.framework.starter.signature;

import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 描述 ：接口签名业务对象
 *
 * @author 小糊涂
 **/
@Getter
public class ApiSignatureBuilder {

    /**
     * 应用的唯一标识
     */
    private final String appId;

    /**
     * 应用key系统分配
     */
    private final String appKey;

    /**
     * 签名数据
     */
    private final String sign;

    /**
     * 临时流水号nonce，至少为10位 ，有效期内防重复提交
     */
    private final String nonce;

    /**
     * 加入timeStamp（时间戳），以服务端当前时间为准，单位为ms ，5分钟内数据有效
     */
    private final long timestamp;

    private ApiSignatureBuilder(String appId, String appKey, String sign, String nonce, long timestamp) {
        this.appId = appId;
        this.appKey = appKey;
        this.sign = sign;
        this.nonce = nonce;
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String generateSign() {
        return appId.concat(appKey).concat(nonce).concat(String.valueOf(timestamp));
    }

    public static final class Builder {
        /**
         * 应用的唯一标识
         */
        private String appId;
        /**
         * 应用key系统分配
         */
        private String appKey;

        /**
         * 签名数据
         */
        private String sign;

        /**
         * 临时流水号nonce，至少为10位 ，有效期内防重复提交
         */
        private String nonce;

        /**
         * 加入timeStamp（时间戳），以服务端当前时间为准，单位为ms ，5分钟内数据有效
         */
        private long timestamp;

        Builder() {
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder appKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public Builder nonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public Builder timestamp(String timestamp) {
            if (!StringUtils.hasText(timestamp)) throw new ApiSignatureException("timestamp is null!");
            this.timestamp = Long.parseLong(timestamp);
            return this;
        }

        public ApiSignatureBuilder build() {
            return new ApiSignatureBuilder(this.appId, this.appKey, this.sign, this.nonce, this.timestamp);
        }
    }

}