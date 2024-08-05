package com.xht.cloud.admin.module.log.domain.request;

import com.xht.cloud.admin.api.log.enums.OperationStatus;
import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：系统操作日志记录-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysLogRequest(系统操作日志记录-查询请求信息)")
public class SysOperationLogQueryRequest extends PageQueryRequest {

    /**
     * 服务名称
     */
    @Schema(description = "服务名称")
    private String serverName;

    /**
     * 业务名称
     */
    @Schema(description = "业务名称")
    private String busName;

    /**
     * 操作状态（0正常1异常）
     */
    @Schema(description = "操作状态（0正常1异常）")
    private OperationStatus status;

}
