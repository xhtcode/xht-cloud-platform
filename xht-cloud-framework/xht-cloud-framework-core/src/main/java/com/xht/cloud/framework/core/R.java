package com.xht.cloud.framework.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xht.cloud.framework.core.trace.TraceIdUtils;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述 ：响应数据父类
 *
 * @author 小糊涂
 **/
@Getter
@SuppressWarnings("unused")
@Schema(description = "响应结果包装")
@JsonPropertyOrder(value = {"success", "code", "msg", "traceId", "data"})
public final class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成功标识
     */
    @Schema(description = "成功标识", example = "true")
    private Boolean success;

    /**
     * 请求链路 ID
     */
    @Schema(description = "请求链路")
    private final String traceId;
    /**
     * 响应状态码
     */
    @Schema(description = "状态码", example = "ok")
    @JsonProperty("code")
    private Integer code;
    /**
     * 响应消息
     */
    @Schema(description = "响应消息", example = "请求成功")
    private String msg;
    /**
     * 响应数据
     */
    @Schema(description = "响应数据", name = "data", subTypes = Objects.class)
    private T data;

    public R() {
        this.traceId = TraceIdUtils.getTraceId();
    }

    private R(Boolean success) {
        this.success = success;
        this.traceId = TraceIdUtils.getTraceId();
    }

    /**
     * 创建一个返回对象
     *
     * @param <T> 数据类型
     * @return {@link R}
     */
    public static <T> R<T> ok() {
        return new R<T>(true).format(GlobalErrorStatusCode.SUCCESS);
    }

    /**
     * 创建一个成功的返回对象
     *
     * @param data 返回数据
     * @param <T>  数据类型
     * @return {@link R}
     */
    public static <T> R<T> ok(T data) {
        return new R<T>(true).format(GlobalErrorStatusCode.SUCCESS).setData(data);
    }

    /**
     * 创建一个成功的返回对象
     *
     * @param msg  描述信息
     * @param data 返回数据
     * @param <T>  数据类型
     * @return {@link R}
     */
    public static <T> R<T> ok(String msg, T data) {
        return new R<T>(true).format(GlobalErrorStatusCode.SUCCESS).setMsg(msg).setData(data);
    }

    /**
     * 创建一个成功的返回对象
     *
     * @param <T> 数据类型
     * @return {@link R}
     */
    public static <T> R<T> failed() {
        return new R<T>(false).format(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 创建一个成功的返回对象
     *
     * @param msg 描述信息
     * @param <T> 数据类型
     * @return {@link R}
     */
    public static <T> R<T> failed(String msg) {
        return new R<T>(false).format(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR).setMsg(msg);
    }

    /**
     * 创建一个失败的返回对象
     *
     * @param status 状态{@link IErrorStatusCode}
     * @param <T>    数据类型
     * @return {@link R}
     */
    public static <T> R<T> failed(IErrorStatusCode status) {
        return new R<T>(false).format(status);
    }

    /**
     * 创建一个失败的返回对象
     *
     * @param status 状态{@link IErrorStatusCode}
     * @param data   返回数据
     * @param <T>    数据类型
     * @return {@link R}
     */
    public static <T> R<T> failed(IErrorStatusCode status, T data) {
        return new R<T>(false).setCode(status.getCode()).setMsg(status.getMessage()).setData(data);
    }

    /**
     * 创建一个返回对象
     *
     * @param success 成功 true 失败false
     * @param <T>     数据类型
     * @return {@link R}
     */
    public static <T> R<T> create(Boolean success) {
        return new R<>(success);
    }

    @JsonIgnore
    public R<T> format(IErrorStatusCode errorStatusCode) {
        this.code = errorStatusCode.getCode();
        this.msg = errorStatusCode.getMessage();
        return this;
    }

    @JsonIgnore
    public R<T> setCode(Integer code) {
        Assert.notNull(code, "状态码不能为空");
        this.code = code;
        return this;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @JsonIgnore
    public R<T> clearMsg() {
        this.msg = null;
        return this;
    }

    @JsonIgnore
    public R<T> appendMsg(String msg) {
        this.msg = String.format("%s%s", this.msg, msg);
        return this;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}
