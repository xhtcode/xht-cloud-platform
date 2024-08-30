package com.xht.cloud.framework.web.handler;


import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.GlobalException;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.web.validation.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自定义全局异常
 *
 * @author 小糊涂
 * @see Exception
 * @see Throwable
 * @see GlobalException
 * @see SysException
 * @see BizException
 * @see BindException
 * @see NoHandlerFoundException
 * @see NoResourceFoundException
 **/
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public DefaultGlobalExceptionHandler() {
        log.debug(">>>>>>web-start 自定义 Controller 全局 异常 拦截<<<<<<");
    }

    /**
     * 捕获 {@link Exception} 异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.<String>failed(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR).setMsg(e.getMessage());
    }

    /**
     * 捕获 {@link Throwable} 异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handleThrowable(Throwable e) {
        log.error("未知异常: {}", e.getMessage(), e);
        return R.<String>failed(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR).setMsg(e.getMessage());
    }

    /**
     * 捕获 {@link GlobalException} , {@link SysException} , {@link BizException} 异常
     */
    @ExceptionHandler(value = {GlobalException.class, SysException.class, BizException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(GlobalException e) {
        log.error("自定义异常: code={} message={}", e.getCode(), e.getMessage(), e);
        return R.<String>create(false).setCode(e.getCode()).setMsg(e.getMessage());
    }

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     *
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("非法参数,message = {}", exception.getMessage(), exception);
        return R.failed(GlobalErrorStatusCode.PARAMS_ERROR);
    }


    /**
     * 验签异常拦截 {@link ApiSignatureException}
     */
    @ExceptionHandler(ApiSignatureException.class)
    public R<String> handle(ApiSignatureException e, HttpServletRequest request) {
        log.error(" {} 请求URL签名失败: {}", request.getRequestURI(), e.getMessage(), e);
        return R.<String>failed(ApiSignatureErrorStatusCode.SIGNATURE_ERROR).appendMsg(String.format(":(%s)", e.getMessage()));
    }

    /**
     * 用户异常 {@link ApiSignatureException}
     */
    @ExceptionHandler(UserException.class)
    public R<String> handle(UserException e, HttpServletRequest request) {
        log.error(" {} 用户异常: {}", request.getRequestURI(), e.getMessage(), e);
        return R.<String>failed().setCode(e.getCode()).setMsg(e.getMessage());
    }

    /**
     * controller 接口拦截  {@link NoHandlerFoundException}
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<String> handle(NoHandlerFoundException e, HttpServletRequest request) {
        log.debug(" {} 请求URL404: {}", request.getRequestURI(), e.getMessage(), e);
        return R.<String>create(false).format(GlobalErrorStatusCode.NOT_FOUND).setMsg("请求资源不存在").appendMsg(String.format(": %s", request.getRequestURI()));
    }

    /**
     * 静态资源拦截 {@link NoResourceFoundException}
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<String> handle(NoResourceFoundException e, HttpServletRequest request) {
        log.debug(" {} 请求URL404: {}", request.getRequestURI(), e.getMessage(), e);
        return R.<String>create(false).format(GlobalErrorStatusCode.NOT_FOUND).appendMsg(String.format(": %s", request.getRequestURI()));
    }

    /**
     * 错误的请求  {@link HttpRequestMethodNotSupportedException}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.debug(" {} 请求URL404: {}", request.getRequestURI(), e.getMessage(), e);
        return R.<String>create(false).format(GlobalErrorStatusCode.NOT_FOUND).appendMsg(String.format(": %s", request.getRequestURI()));
    }

    /**
     * jsr 303校验异常捕获
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Map<String, Object>> handleException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> resultMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(fieldErrors)) {
                for (FieldError fieldError : fieldErrors) {
                    resultMap.put(fieldError.getField(), StringUtils.emptyToDefault(fieldError.getDefaultMessage(), GlobalErrorStatusCode.PARAMS_ERROR.getMessage()));
                }
            }
        }
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,data={}", requestURI, request.getMethod(), resultMap, e);
        return R.failed(GlobalErrorStatusCode.PARAMS_ERROR, resultMap);
    }

    /**
     * 校验异常捕获 扩展
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Map<String, Object>> handleException(ValidationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(e.getFiledName(), e.getMessage());
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,data={}", requestURI, request.getMethod(), resultMap, e);
        return R.failed(GlobalErrorStatusCode.PARAMS_ERROR, resultMap);
    }
}
