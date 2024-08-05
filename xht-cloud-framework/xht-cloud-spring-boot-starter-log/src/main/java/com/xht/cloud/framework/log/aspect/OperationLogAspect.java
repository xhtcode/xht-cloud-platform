package com.xht.cloud.framework.log.aspect;

import com.xht.cloud.admin.api.log.dto.OperationLogDTO;
import com.xht.cloud.admin.api.log.enums.OperationStatus;
import com.xht.cloud.admin.api.log.feign.OperationLogClient;
import com.xht.cloud.framework.core.constant.LogConstant;
import com.xht.cloud.framework.core.constant.SpringPropertiesNameConstant;
import com.xht.cloud.framework.core.rpc.RpcConstants;
import com.xht.cloud.framework.log.annotation.OperationLog;
import com.xht.cloud.framework.utils.jackson.JsonUtils;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import com.xht.cloud.framework.utils.web.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：业务操作日志的切面
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
public class OperationLogAspect {

    @Value(SpringPropertiesNameConstant.SPRING_APPLICATION_KEY_SPEL)
    private final String applicationName;

    private final OperationLogClient operationLogClient;

    public OperationLogAspect(String applicationName, OperationLogClient operationLogClient) {
        this.applicationName = applicationName;
        this.operationLogClient = operationLogClient;
        log.debug(">>>>>>log-start 业务操作日志切面【{}】<<<<<<", applicationName);
    }

    /**
     * 定义切面需要使用的注释
     */
    @Pointcut("@annotation(com.xht.cloud.framework.log.annotation.OperationLog)")
    public void point() {
    }
    /**
     * 数据审计日志切点
     *
     * @param joinPoint    {@link ProceedingJoinPoint}
     * @param operationLog {@link OperationLog}
     * @return Object
     */
    @Around(value = "point() && @annotation(operationLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, OperationLog operationLog) throws Throwable {
        Long startTime = System.currentTimeMillis();
        OperationLogDTO operationLogDTO = new OperationLogDTO();
        Object result = null;
        try {
            result = joinPoint.proceed();
            operationLogDTO.setStatus(OperationStatus.YES);
        } catch (Exception e) {
            operationLogDTO.setStatus(OperationStatus.NO);
            operationLogDTO.setErrorMsg(e.getMessage());
            log.info("【业务操作日志切面】异常 e = {}", e.getMessage(), e);
        } finally {
            try {
                operationLogDTO.setServerName(applicationName);
                operationLogDTO.setBusName(operationLog.value());
                operationLogDTO.setBusDesc(operationLog.description());
                operationLogDTO.setOperateType(operationLog.operateType());
                HttpServletRequest request = HttpServletUtils.getRequest();
                if (Objects.nonNull(request)) {
                    String method = request.getMethod();
                    operationLogDTO.setOperateMethod(method);
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("requestParams", HttpServletUtils.parseRequestParams(request));
                    parameters.put("requestHeader", HttpServletUtils.getHeaders(request));
                    if (!RpcConstants.GET.equals(method)) {
                        parameters.put("requestBody", HttpServletUtils.getBody(request));
                    }
                    operationLogDTO.setOperateParam(JsonUtils.toJsonString(parameters));
                    operationLogDTO.setRequestUrl(request.getRequestURI());
                    operationLogDTO.setOperateIp(IPUtils.getIp(request));
                } else {
                    operationLogDTO.setOperateParam(JsonUtils.toJsonString(getOperateParam(((CodeSignature) joinPoint.getSignature()).getParameterNames(), joinPoint.getArgs())));
                }
                operationLogDTO.setTraceId(HttpServletUtils.getHeader(request, LogConstant.TRACE_ID));
                Long endTime = System.currentTimeMillis();
                operationLogDTO.setOperateTime(endTime - startTime);
                operationLogClient.saveOperationLog(operationLogDTO);
            } catch (Exception e) {
                log.info("日志保存错误 e = {}", e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 获取接口详情
     */
    private Map<String, Object> getOperateParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> resultMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            String paramClass = paramNames[i].getClass().toString();
            String paramType = paramClass.substring(paramClass.lastIndexOf(".") + 1);
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];
            resultMap.put("name", paramName);
            resultMap.put("type", paramType);
            resultMap.put("value", paramValue);
        }
        return resultMap;
    }

}
