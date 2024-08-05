package com.xht.cloud.framework.web.signature;

import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import com.xht.cloud.framework.web.constant.ApiSignatureConstant;
import com.xht.cloud.framework.web.signature.chain.ApiSignatureChain;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Objects;

/**
 * 描述 ：接口签名切面
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
public class ApiSignatureAspect {

    @Resource
    private ApiSignatureChain apiSignatureChain;

    @Before("@annotation(com.xht.cloud.framework.web.signature.ApiSignature)")
    public void doBefore() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (Objects.isNull(request)) {
            throw new RuntimeException("请求异常，请检查!");
        }
        String appId = request.getHeader(ApiSignatureConstant.APP_ID);
        String appKey = request.getHeader(ApiSignatureConstant.APP_KEY);
        String sign = request.getHeader(ApiSignatureConstant.SIGN);
        String nonce = request.getHeader(ApiSignatureConstant.NONCE);
        String timestamp = request.getHeader(ApiSignatureConstant.TIMESTAMP);
        apiSignatureChain.doFilter(ApiSignatureBuilder.builder()
                .appId(appId)
                .appKey(appKey)
                .sign(sign)
                .nonce(nonce)
                .timestamp(timestamp)
                .build(), request);
    }
}
