package com.xht.cloud.admin.module.dept.controller;

import com.xht.cloud.admin.module.dept.domain.request.SysPositionCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysPositionResponse;
import com.xht.cloud.admin.module.dept.service.ISysPositionService;
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
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/position")
@RequiredArgsConstructor
@Tag(name = "岗位信息")
public class SysPositionController {

    private final ISysPositionService sysPositionService;

    /**
     * 创建
     *
     * @param createRequest {@link SysPositionCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-岗位信息")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:position:add')")
    public R<Boolean> create(@Validated @RequestBody SysPositionCreateRequest createRequest) {
        sysPositionService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysPositionUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-岗位信息")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:position:edit')")
    public R<Boolean> update(@Validated @RequestBody SysPositionUpdateRequest updateRequest) {
        sysPositionService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-岗位信息")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:position:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysPositionService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysPositionResponse}
     */
    @Operation(summary = "根据id查询详细-岗位信息")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysPositionResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysPositionService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysPositionQueryRequest}
     * @return {@link SysPositionResponse} 分页详情
     */
    @Operation(summary = "分页查询-岗位信息")
    @GetMapping
    public R<PageResponse<SysPositionResponse>> findPage(SysPositionQueryRequest queryRequest) {
        return R.ok(sysPositionService.findPage(queryRequest));
    }

}
