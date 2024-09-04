package com.xht.cloud.framework.openfeign.filter;

import com.xht.cloud.framework.domain.KeyValue;
import com.xht.cloud.framework.openfeign.client.ApiSignatureClient;
import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.starter.signature.ApiSignatureType;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.utils.ROptional;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ：数据库过滤请求
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureOpenFeignFilter implements ApiSignatureFilter {
    private final ApiSignatureProperties apiSignatureProperties;
    private final ApiSignatureClient apiSignatureClient;


    public ApiSignatureOpenFeignFilter(ApiSignatureProperties apiSignatureProperties, ApiSignatureClient apiSignatureClient) {
        this.apiSignatureProperties = apiSignatureProperties;
        this.apiSignatureClient = apiSignatureClient;
        log.debug(">>>>>>openfeign-start 接口签名过滤器 验签 <<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param apiSignatureBuilder {@link ApiSignatureBuilder}
     * @param request             {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder apiSignatureBuilder, HttpServletRequest request) {
        if (Objects.equals(ApiSignatureType.RPC, apiSignatureProperties.getType())) {
            R<KeyValue<String, String>> appId1 = apiSignatureClient.findAppId(apiSignatureBuilder.getAppId());
            KeyValue<String, String> keyValue = ROptional.of(appId1).getData().orElseThrow(() -> new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_INVALID));
            String appId = keyValue.getKey();
            String appKey = keyValue.getValue();
            if (!Objects.equals(apiSignatureBuilder.getAppId(), appId)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_NOT_FOUNT);
            }
            if (!Objects.equals(apiSignatureBuilder.getAppKey(), appKey)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_KEY_NOT_FOUNT);
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
