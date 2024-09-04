package com.xht.cloud.admin.module.log.controller;

import com.xht.cloud.admin.module.log.domain.request.SysOperationLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysOperationLogResponse;
import com.xht.cloud.admin.module.log.service.ISysOperationLogService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.domain.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：系统操作日志记录
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/operation/log")
@RequiredArgsConstructor
@Tag(name = "操作日志")
public class SysOperationLogController {

    private final ISysOperationLogService sysOperationLogService;

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysOperationLogResponse}
     */
    @Operation(summary = "根据id查询详细-操作日志")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysOperationLogResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysOperationLogService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysOperationLogQueryRequest}
     * @return {@link SysOperationLogResponse} 分页详情
     */
    @Operation(summary = "分页查询-操作日志")
    @GetMapping
    public R<PageResponse<SysOperationLogResponse>> findPage(SysOperationLogQueryRequest queryRequest) {
        return R.ok(sysOperationLogService.findPage(queryRequest));
    }

}
