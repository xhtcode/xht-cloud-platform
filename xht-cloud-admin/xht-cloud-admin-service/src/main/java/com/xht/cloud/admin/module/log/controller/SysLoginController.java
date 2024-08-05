package com.xht.cloud.admin.module.log.controller;

import com.xht.cloud.admin.module.log.domain.request.SysLoginLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.admin.module.log.service.ISysLoginService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
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
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/login/log")
@RequiredArgsConstructor
@Tag(name = "系统登录日志记录")
public class SysLoginController {
    private final ISysLoginService sysLoginService;

    /**
     * 根据id查询详细登录日志
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysLoginLogResponse}
     */
    @Operation(summary = "根据id查询详细-登录日志")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysLoginLogResponse> findById(@PathVariable("id") Long id) {
        return R.ok(sysLoginService.findById(id));
    }

    /**
     * 分页查询登录日志
     *
     * @param queryRequest {@link SysLoginLogQueryRequest}
     * @return {@link SysLoginLogResponse} 分页详情
     */
    @Operation(summary = "分页查询-登录日志")
    @GetMapping
    public R<PageResponse<SysLoginLogResponse>> findPage(SysLoginLogQueryRequest queryRequest) {
        return R.ok(sysLoginService.findPage(queryRequest));
    }
}
