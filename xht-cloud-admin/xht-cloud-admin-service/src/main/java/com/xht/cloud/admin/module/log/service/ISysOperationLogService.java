package com.xht.cloud.admin.module.log.service;

import com.xht.cloud.admin.api.log.dto.OperationLogDTO;
import com.xht.cloud.admin.module.log.domain.request.SysOperationLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysOperationLogResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

/**
 * 描述 ：系统操作日志记录
 *
 * @author 小糊涂
 **/
public interface ISysOperationLogService {

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysOperationLogResponse}
     */
    SysOperationLogResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysOperationLogQueryRequest}
     * @return {@link SysOperationLogResponse} 分页详情
     */
    PageResponse<SysOperationLogResponse> findPage(SysOperationLogQueryRequest queryRequest);

    /**
     * 保存操作日志
     *
     * @param operationLogDTO {@link OperationLogDTO}
     */
    void saveOperationLog(OperationLogDTO operationLogDTO);
}
