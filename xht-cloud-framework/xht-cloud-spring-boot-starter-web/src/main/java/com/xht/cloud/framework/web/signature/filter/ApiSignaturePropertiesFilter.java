package com.xht.cloud.framework.web.signature.filter;

import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.starter.signature.ApiSignatureType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ： 接口签名过滤器>>>>>> properties 查验 签名 <<<<<<
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignaturePropertiesFilter implements ApiSignatureFilter {

    private final ApiSignatureProperties apiSignatureProperties;

    public ApiSignaturePropertiesFilter(ApiSignatureProperties apiSignatureProperties) {
        this.apiSignatureProperties = apiSignatureProperties;
        log.debug(">>>>>>web-start 接口签名过滤器 properties查验签名信息是否正确<<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param apiSignatureBuilder {@link ApiSignatureBuilder}
     * @param request             {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder apiSignatureBuilder, HttpServletRequest request) {
        if (Objects.equals(ApiSignatureType.PROPERTIES, apiSignatureProperties.getType())) {
            String appId = apiSignatureProperties.getAppId();
            String appKey = apiSignatureProperties.getAppKey();
            if (!Objects.equals(apiSignatureBuilder.getAppId(), appId)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_INVALID);
            }
            if (!Objects.equals(apiSignatureBuilder.getAppKey(), appKey)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_KEY_ERROR);
            }
        }
    }

    /**
     * 排序
     *
     * @return 数值越少越前 越大就越后
     */
    @Override
    public Integer getSort() {
        return MAX_SORT - 1;
    }

}
