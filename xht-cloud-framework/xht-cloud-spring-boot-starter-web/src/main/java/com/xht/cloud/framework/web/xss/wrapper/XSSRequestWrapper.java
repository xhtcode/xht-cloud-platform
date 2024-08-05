package com.xht.cloud.framework.web.xss.wrapper;

import cn.hutool.http.HTMLFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述 ：xss 请求信息过滤
 *
 * @author 小糊涂
 **/
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private final HTMLFilter htmlFilter = new HTMLFilter();

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    // ============================ parameter ============================
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = htmlFilter.filter(values[i]);
            }
            map.put(entry.getKey(), values);
        }
        return map;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlFilter.filter(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return htmlFilter.filter(value);
    }

    // ============================ attribute ============================
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
            return htmlFilter.filter((String) value);
        }
        return value;
    }

    // ============================ header ============================
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlFilter.filter(value);
    }

    // ============================ queryString ============================
    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlFilter.filter(value);
    }

}