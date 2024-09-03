package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserStaffResponse;
import com.xht.cloud.admin.module.user.service.ISysUserStaffService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.security.core.PermissionCheckService;
import com.xht.cloud.framework.utils.jackson.desensitization.SkipSensitiveThreadLocal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：工作人员
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/user/staff")
@RequiredArgsConstructor
@Tag(name = "工作人员")
public class SysUserStaffController {

    private final ISysUserStaffService sysUserService;

    private final PermissionCheckService permissionCheckService;

    /**
     * 创建
     *
     * @param createRequest {@link SysUserStaffCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-用户")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:add')")
    public R<String> create(@Validated @RequestBody SysUserStaffCreateRequest createRequest) {
        return ok(sysUserService.create(createRequest));
    }

    /**
     * 根据id修改
     *
     * @param request {@link SysUserStaffUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-用户")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:edit')")
    public R<Boolean> update(@Validated @RequestBody SysUserStaffUpdateRequest request) {
        return ok(sysUserService.update(request));
    }

    /**
     * 根据id删除
     *
     * @param userIds {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-用户")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:remove')")
    public R<Boolean> remove(@RequestBody List<String> userIds) {
        return ok( sysUserService.remove(userIds));
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserStaffResponse}
     */
    @Operation(summary = "根据id查询详细-用户")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/detail/{id}")
    public R<SysUserStaffResponse> findById(@PathVariable("id") String id) {
        if (permissionCheckService.checkCurrentUserId(id)) {
            SkipSensitiveThreadLocal.skipAll();
        }
        return R.ok(sysUserService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link SysUserStaffResponse} 分页详情
     */
    @Operation(summary = "分页查询-用户")
    @GetMapping
    public R<PageResponse<SysUserStaffResponse>> findPage(SysUserQueryRequest queryRequest) {
        return R.ok(sysUserService.findPage(queryRequest));
    }


}
