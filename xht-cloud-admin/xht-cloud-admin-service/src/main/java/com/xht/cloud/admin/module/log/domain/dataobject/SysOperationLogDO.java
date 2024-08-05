package com.xht.cloud.admin.module.log.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.log.enums.OperationStatus;
import com.xht.cloud.framework.core.enums.OperateTypeEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseNoneDeleteDO;
import lombok.Data;

/**
 * 描述 ：系统操作日志记录
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_operation_log")
public class SysOperationLogDO extends BaseNoneDeleteDO {

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 服务名称
     */
    @TableField(value = "server_name")
    private String serverName;

    /**
     * 业务名称
     */
    @TableField(value = "bus_name")
    private String busName;

    /**
     * 业务描述
     */
    @TableField(value = "bus_desc")
    private String busDesc;

    /**
     * 请求方式
     */
    @TableField(value = "operate_method")
    private String operateMethod;

    /**
     * 操作类别（0其它1后台用户2手机端用户）
     */
    @TableField(value = "operate_type")
    private OperateTypeEnums operateType;

    /**
     * 操作人员
     */
    @TableField(value = "operate_name")
    private String operateName;

    /**
     * 请求URL
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 主机地址
     */
    @TableField(value = "operate_ip")
    private String operateIp;

    /**
     * 操作地点
     */
    @TableField(value = "operate_time")
    private Long operateTime;

    /**
     * 请求参数
     */
    @TableField(value = "operate_param")
    private String operateParam;

    /**
     * 日志TRACE_ID
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 操作状态（0正常1异常）
     */
    @TableField(value = "status")
    private OperationStatus status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

}
