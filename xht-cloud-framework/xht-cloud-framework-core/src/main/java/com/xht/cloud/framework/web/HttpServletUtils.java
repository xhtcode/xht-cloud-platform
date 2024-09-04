package com.xht.cloud.framework.web;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.jackson.JsonUtils;
import com.xht.cloud.framework.utils.StringUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 描述 ：{@link jakarta.servlet.http.HttpServlet} 相关操作
 *
 * @author 小糊涂
 **/
@Slf4j
public final class HttpServletUtils {

    /**
     * 获得请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    /**
     * 获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param name    头信息的KEY
     */
    public static String getHeader(HttpServletRequest request, String name) {
        return getHeader(request, name, null);
    }


    // --------------------------------------------------------- getParam start

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    public static MultiValueMap<String, String> getQueryParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameterMap.forEach((key, values) -> {
            String queryString = StringUtils.hasText(request.getQueryString()) ? request.getQueryString() : "";
            if (queryString.contains(key) && !ArrayUtil.isEmpty(values)) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }

    /**
     * 请求对象构建MultiValueMap.
     *
     * @param request 请求对象
     * @return MultiValueMap
     */
    public static Map<String, Object> getMapParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parameters = new HashMap<>();
        parameterMap.forEach((k, v) -> parameters.put(k, StringUtils.arrayToDelimitedString(v, ",")));
        return parameters;
    }

    /**
     * 请求对象构建MultiValueMap.
     *
     * @param request 请求对象
     * @return MultiValueMap
     */
    public static MultiValueMap<String, String> getMultiValueMapParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((k, v) -> parameters.addAll(k, Arrays.asList(v)));
        return parameters;
    }

    /**
     * 把请求的参数格式化成字符串
     *
     * @param request {@link ServletRequest}
     * @return 格式化后的字符串
     */
    public static String parseRequestParams(ServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        if (Objects.isNull(requestParams)) return StringConstant.EMPTY_STR;
        StringBuilder builder = new StringBuilder();
        Map<String, String[]> params = new HashMap<>(requestParams);
        var ref = new Object() {
            Integer count = 0;
        };
        params.forEach((k, v) -> {
            for (String s : v) {
                if (ref.count == 0) {
                    builder.append(String.format("?%s=%s", k, s));
                    ref.count++;
                } else {
                    builder.append(String.format("&%s=%s", k, s));
                }

            }
        });
        return builder.toString();
    }

    /**
     * 获取请求体<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体
     */
    public static String getBody(ServletRequest request) {
        try (final BufferedReader reader = request.getReader()) {
            if (Objects.isNull(reader)) return StringConstant.EMPTY_STR;
            return IoUtil.read(reader);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取请求体byte[]<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体byte[]
     */
    public static byte[] getBodyBytes(ServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    // --------------------------------------------------------- getParam end

    /**
     * 获得请求header中的信息
     *
     * @param request      请求对象{@link HttpServletRequest}
     * @param name         头信息的KEY
     * @param defaultValue 默认值
     */
    public static String getHeader(HttpServletRequest request, String name, String defaultValue) {
        return StringUtils.emptyToDefault(request.getHeader(name), defaultValue);
    }

    /**
     * 获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            result.put(name, getHeader(request, name));
        }
        return result;
    }
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param obj      待渲染对象
     */
    public static void writeString(HttpServletResponse response, Object obj) {
        writeString(response, HttpStatus.OK, obj);
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param obj      待渲染对象
     */
    public static void writeString(HttpServletResponse response, HttpStatus httpStatus, Object obj) {
        PrintWriter writer = null;
        try {
            response.setStatus(httpStatus.value());
            // 允许跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 允许自定义请求头token(允许head跨域)
            response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.print(JsonUtils.toJsonString(obj));
        } catch (IOException e) {
            log.error("响应错误 {}", e.getMessage(), e);
        } finally {
            if (Objects.nonNull(writer)) {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception ignored) {

                }
            }
        }
    }

    public static String getParams(HttpServletRequest request, String name) {
        if (Objects.isNull(request)) return null;
        return request.getParameter(name);
    }
}
