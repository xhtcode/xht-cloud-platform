package com.xht.cloud.admin.api.log.feign;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.framework.domain.R;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 描述 ：系统操作日志
 *
 * @author : 小糊涂
 **/
@FeignClient(value = "xht-cloud-admin-service", contextId = "sysLoginLogClient")
public interface SysLoginLogClient {

    /**
     * 保存操作日志
     *
     * @param operationLogDTO {@link SysLoginLogDTO}
     * @return 状态
     */
    @PostMapping("/api/sys/login/log")
    R<Boolean> saveLoginLog(@RequestBody SysLoginLogDTO operationLogDTO);
}
