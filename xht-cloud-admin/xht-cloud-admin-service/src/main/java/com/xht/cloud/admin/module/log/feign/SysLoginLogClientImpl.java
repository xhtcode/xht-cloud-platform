package com.xht.cloud.admin.module.log.feign;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.api.log.feign.SysLoginLogClient;
import com.xht.cloud.admin.module.log.service.ISysLoginService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Tag(name = "系统登录日志（内部专用）")
@RestController
@RequiredArgsConstructor
public class SysLoginLogClientImpl implements SysLoginLogClient {

    private final ISysLoginService sysLoginService;

    /**
     * 保存系统登录日志
     *
     * @param loginLogDTO {@link SysLoginLogDTO}
     * @return 状态
     */
    @Override
    @Operation(summary = "保存系统登录日志")
    @SkipAuthentication
    public R<Boolean> saveLoginLog(@RequestBody SysLoginLogDTO loginLogDTO) {
        sysLoginService.saveOperationLog(loginLogDTO);
        return R.ok(Boolean.TRUE);
    }
}
