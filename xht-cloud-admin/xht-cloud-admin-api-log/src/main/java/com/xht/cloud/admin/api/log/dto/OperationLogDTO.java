package com.xht.cloud.admin.api.log.dto;

import com.xht.cloud.admin.api.log.enums.OperationStatus;
import com.xht.cloud.framework.core.enums.OperateTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 ：业务操作日志的 Bo
 *
 * @author 小糊涂
 **/
@Data
public class OperationLogDTO implements Serializable {

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 业务名称
     */
    private String busName;

    /**
     * 业务描述
     */
    private String busDesc;

    /**
     * 请求方式
     */
    private String operateMethod;


    /**
     * 操作类别
     */
    private OperateTypeEnums operateType;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 主机地址
     */
    private String operateIp;


    /**
     * 请求参数
     */
    private String operateParam;

    /**
     * 日志TRACE_ID
     */
    private String traceId;

    /**
     * 操作状态（0正常1异常）
     */
    private OperationStatus status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 执行时间
     */
    private Long operateTime;


}
