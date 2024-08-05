package com.xht.cloud.framework.web.signature.filter;

import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import com.xht.cloud.sdk.signature.SignatureUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ：接口签名过滤器 最终签名认证
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureAuthenticationFilter implements ApiSignatureFilter {


    public ApiSignatureAuthenticationFilter() {
        log.debug(">>>>>>web-start 接口签名过滤器 签名认证<<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param apiSignatureBuilder {@link ApiSignatureBuilder}
     * @param request             {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder apiSignatureBuilder, HttpServletRequest request) {
        String sourceSign = apiSignatureBuilder.getSign();
        String generateSign = SignatureUtil.generateSignature(apiSignatureBuilder.generateSign(), request.getParameterMap(), HttpServletUtils.getBody(request));
        if (!Objects.equals(sourceSign, generateSign)) {
            throw new ApiSignatureException(ApiSignatureErrorStatusCode.SIGNATURE_ERROR);
        }
    }

    /**
     * 排序
     *
     * @return 数值越少越前 越大就越后
     */
    @Override
    public Integer getSort() {
        return MAX_SORT;
    }

}