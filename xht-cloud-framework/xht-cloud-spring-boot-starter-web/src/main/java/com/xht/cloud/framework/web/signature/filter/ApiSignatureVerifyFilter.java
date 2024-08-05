package com.xht.cloud.framework.web.signature.filter;

import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 描述 ：接口签名过滤器>>>>>> 基础数据 查验 签名 <<<<<<
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureVerifyFilter implements ApiSignatureFilter {

    private final ApiSignatureProperties apiSignatureProperties;

    public ApiSignatureVerifyFilter(ApiSignatureProperties apiSignatureProperties) {
        this.apiSignatureProperties = apiSignatureProperties;
        log.debug(">>>>>>web-start 接口签名过滤器 基础数据 查验<<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param apiSignatureBuilder {@link ApiSignatureBuilder}
     * @param request             {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder apiSignatureBuilder, HttpServletRequest request) {
        String appId = apiSignatureBuilder.getAppId();
        String appKey = apiSignatureBuilder.getAppKey();
        String sign = apiSignatureBuilder.getSign();
        String nonce = apiSignatureBuilder.getNonce();
        long timeOut = apiSignatureProperties.getTimeOut();
        long timestamp = apiSignatureBuilder.getTimestamp();
        if (!StringUtils.hasText(appId)) throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_NOT_FOUNT);
        if (!StringUtils.hasText(appKey))
            throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_KEY_NOT_FOUNT);
        if (!StringUtils.hasText(sign)) throw new ApiSignatureException("sign is null!");
        if (!StringUtils.hasText(nonce)) throw new ApiSignatureException("nonce is null!");
        if (10 > nonce.length()) throw new ApiSignatureException("nonce Insufficient length !");
        long nowTimestamp = System.currentTimeMillis();
        long maxTimestamp = timestamp + timeOut;
        long minTimestamp = timestamp - timeOut;
        if (nowTimestamp > maxTimestamp || nowTimestamp < minTimestamp) {
            throw new ApiSignatureException(ApiSignatureErrorStatusCode.TIMESTAMP_TIME_OUT);
        }
    }

}
