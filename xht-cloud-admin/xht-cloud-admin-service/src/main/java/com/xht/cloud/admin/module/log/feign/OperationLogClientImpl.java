package com.xht.cloud.admin.module.log.feign;

import com.xht.cloud.admin.api.log.dto.OperationLogDTO;
import com.xht.cloud.admin.api.log.feign.OperationLogClient;
import com.xht.cloud.admin.module.log.service.ISysOperationLogService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：系统操作日志内部专用
 *
 * @author 小糊涂
 **/
@Tag(name = "系统操作日志（内部专用）")
@RestController
@RequiredArgsConstructor
public class OperationLogClientImpl implements OperationLogClient {

    private final ISysOperationLogService sysOperationLogService;

    /**
     * 保存操作日志
     *
     * @param operationLogDTO {@link OperationLogDTO}
     */
    @Override
    @SkipAuthentication
    @Operation(summary = "保存操作日志")
    public R<Boolean> saveOperationLog(@RequestBody OperationLogDTO operationLogDTO) {
        sysOperationLogService.saveOperationLog(operationLogDTO);
        return R.ok(Boolean.TRUE);
    }
}
