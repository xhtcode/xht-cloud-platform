package com.xht.cloud.framework.redis.filter;

import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.redis.constant.RedisConstant;
import com.xht.cloud.framework.redis.key.RedisKeyTool;
import com.xht.cloud.framework.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 描述 ：验签校验随机数是否重复
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureNonceRepeatFilter implements ApiSignatureFilter {

    private final ApiSignatureProperties apiSignatureProperties;

    private final RedisService redisService;

    public ApiSignatureNonceRepeatFilter(ApiSignatureProperties apiSignatureProperties, RedisService redisService) {
        this.apiSignatureProperties = apiSignatureProperties;
        this.redisService = redisService;
        log.debug(">>>>>>redis-start 接口签名过滤器 验签校验随机数是否重复 <<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param signatureBuilder {@link ApiSignatureBuilder}
     * @param request          {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder signatureBuilder, HttpServletRequest request) {
        String nonce = signatureBuilder.getNonce();
        String key = RedisKeyTool.createKeyTemplate(RedisConstant.API_SIGNATURE_NONCE_KEY, nonce);
        Long expire = redisService.getExpire(key);
        if (expire >= 0) {
            throw new ApiSignatureException(String.format("临时流水号`%s`重复", nonce));
        } else {
            redisService.set(key, nonce, apiSignatureProperties.getTimeOut(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 排序
     *
     * @return 数值越少越前 越大就越后
     */
    @Override
    public Integer getSort() {
        return 2;
    }
}
