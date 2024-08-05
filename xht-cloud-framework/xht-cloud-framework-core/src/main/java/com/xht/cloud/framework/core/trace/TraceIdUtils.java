package com.xht.cloud.framework.core.trace;

import com.xht.cloud.framework.core.constant.LogConstant;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 描述 ：日志 trace 工具类
 *
 * @author 小糊涂
 **/
public final class TraceIdUtils {

    public static String getTraceId() {
        return MDC.get(LogConstant.TRACE_ID);
    }

    public static void putTraceId(String traceId) {
        MDC.put(LogConstant.TRACE_ID, traceId);
    }

    public static void removeTraceId() {
        MDC.remove(LogConstant.TRACE_ID);
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
