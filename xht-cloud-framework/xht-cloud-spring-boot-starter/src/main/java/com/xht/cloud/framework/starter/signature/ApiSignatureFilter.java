package com.xht.cloud.framework.starter.signature;


import jakarta.servlet.http.HttpServletRequest;

/**
 * 描述 ：接口签名过滤器
 *
 * @author 小糊涂
 **/
public interface ApiSignatureFilter {

    int MAX_SORT = 999;

    /**
     * 过滤链执行器
     *
     * @param signatureBuilder {@link ApiSignatureBuilder}
     * @param request          {@link HttpServletRequest}
     */
    void doFilter(ApiSignatureBuilder signatureBuilder, HttpServletRequest request);

    /**
     * 排序
     *
     * @return 数值越少越前 越大就越后
     */
    default Integer getSort() {
        return 0;
    }

}
