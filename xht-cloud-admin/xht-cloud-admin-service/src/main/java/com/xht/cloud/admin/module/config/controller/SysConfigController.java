package com.xht.cloud.admin.module.config.controller;

import com.xht.cloud.admin.module.config.domain.request.SysConfigCreateRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigQueryRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigUpdateRequest;
import com.xht.cloud.admin.module.config.domain.response.SysConfigResponse;
import com.xht.cloud.admin.module.config.service.ISysConfigService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 描述 ：系统配置信息
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/config")
@RequiredArgsConstructor
@Tag(name = "系统配置信息")
public class SysConfigController {

    private final ISysConfigService sysConfigService;

    /**
     * 创建
     *
     * @param createRequest {@link SysConfigCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-系统配置信息")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:config:add')")
    public R<Boolean> create(@Validated @RequestBody SysConfigCreateRequest createRequest) {
        sysConfigService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysConfigUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-系统配置信息")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:config:edit')")
    public R<Boolean> update(@Validated @RequestBody SysConfigUpdateRequest updateRequest) {
        sysConfigService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-系统配置信息")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:config:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysConfigService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysConfigResponse}
     */
    @Operation(summary = "根据id查询详细-系统配置信息")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysConfigResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysConfigService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysConfigQueryRequest}
     * @return {@link SysConfigResponse} 分页详情
     */
    @Operation(summary = "分页查询-系统配置信息")
    @GetMapping
    public R<PageResponse<SysConfigResponse>> findPage(SysConfigQueryRequest queryRequest) {
        return R.ok(sysConfigService.findPage(queryRequest));
    }

}
