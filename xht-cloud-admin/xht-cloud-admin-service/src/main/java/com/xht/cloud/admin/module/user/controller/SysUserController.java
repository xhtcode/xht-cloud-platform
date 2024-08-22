package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.module.user.domain.request.SysUserBaseAddUpdate;
import com.xht.cloud.admin.module.user.domain.request.SysUserCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserResponse;
import com.xht.cloud.admin.module.user.domain.response.SysUserVo;
import com.xht.cloud.admin.module.user.service.ISysUserService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.core.R;
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
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Tag(name = "用户信息管理")
public class SysUserController {

    private final ISysUserService sysUserService;

    /**
     * 创建
     *
     * @param request {@link SysUserCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-用户")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:add')")
    public R<String> create(@Validated @RequestBody SysUserBaseAddUpdate request) {
        return ok(sysUserService.create(request));
    }

    /**
     * 根据id修改
     *
     * @param request {@link SysUserUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-用户")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:edit')")
    public R<String> update(@Validated @RequestBody SysUserBaseAddUpdate request) {
        return ok(sysUserService.update(request));
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-用户")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysUserService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserResponse}
     */
    @Operation(summary = "根据id查询详细-用户")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/detail/{id}")
    public R<SysUserVo> findById(@PathVariable("id") String id) {
        return R.ok(sysUserService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link SysUserResponse} 分页详情
     */
    @Operation(summary = "分页查询-用户")
    @GetMapping
    public R<PageResponse<SysUserResponse>> findPage(SysUserQueryRequest queryRequest) {
        return R.ok(sysUserService.findPage(queryRequest));
    }

    /**
     * 重置账号密码
     *
     * @param userId 用户id
     */
    @Operation(summary = "重置账号密码")
    @PutMapping("/reset/password/{userId}")
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:user:password:reset')")
    public R<String> resetUserPassword(@PathVariable("userId") String userId) {
        return R.ok(sysUserService.resetUserPassword(userId));
    }
}
