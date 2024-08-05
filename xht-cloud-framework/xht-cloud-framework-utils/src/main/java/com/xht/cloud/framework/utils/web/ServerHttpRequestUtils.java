package com.xht.cloud.framework.utils.web;

import com.xht.cloud.framework.core.constant.StringConstant;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;


/**
 * 响应式请求工具类.
 *
 * @author 小糊涂
 */
public final class ServerHttpRequestUtils {

    /**
     * 获取参数值.
     *
     * @param request   请求对象
     * @param paramName 请求参数名称
     * @return 参数值
     */
    public static String getParamValue(ServerHttpRequest request, String paramName) {
        // 从header中获取
        String paramValue = request.getHeaders().getFirst(paramName);
        // 从参数中获取
        if (StringUtils.isEmpty(paramValue)) {
            paramValue = request.getQueryParams().getFirst(paramName);
        }
        return StringUtils.emptyToDefault(paramValue, StringConstant.EMPTY_STR);
    }

    /**
     * 获取请求路径URL.
     *
     * @param request 请求对象
     * @return 路径URL
     */
    public static String getRequestURL(ServerHttpRequest request) {
        return request.getPath().pathWithinApplication().value();
    }

}
