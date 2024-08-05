package com.xht.cloud.admin.module.area.controller;

import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoCreateRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.domain.response.SysAreaInfoResponse;
import com.xht.cloud.admin.module.area.service.ISysAreaInfoService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.treenode.INode;
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
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/area/info")
@RequiredArgsConstructor
@Tag(name = "地区信息")
public class SysAreaInfoController {

    private final ISysAreaInfoService sysAreaInfoService;

    /**
     * 创建
     *
     * @param createRequest {@link SysAreaInfoCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-地区信息")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:area-info:add')")
    public R<Boolean> create(@Validated @RequestBody SysAreaInfoCreateRequest createRequest) {
        sysAreaInfoService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysAreaInfoUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-地区信息")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:area-info:edit')")
    public R<Boolean> update(@Validated @RequestBody SysAreaInfoUpdateRequest updateRequest) {
        sysAreaInfoService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-地区信息")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:area-info:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysAreaInfoService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAreaInfoResponse}
     */
    @Operation(summary = "根据id查询详细-地区信息")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysAreaInfoResponse> findById(@PathVariable("id") String id) {
        return ok(sysAreaInfoService.findById(id));
    }

    /**
     * 按条件查询全部
     *
     * @param queryRequest {@link SysAreaInfoQueryRequest}
     * @return {@link PageResponse<SysAreaInfoResponse>} 详情
     */
    @Operation(summary = "查询全部-地区信息")
    @GetMapping
    public R<List<SysAreaInfoResponse>> treeList(SysAreaInfoQueryRequest queryRequest) {
        List<SysAreaInfoResponse> list = sysAreaInfoService.list(queryRequest);
        return ok(list);
    }

    @Operation(summary = "查询全部-地区信息")
    @GetMapping("/tree")
    public R<List<INode<String>>> info() {
        return ok(sysAreaInfoService.convert(new SysAreaInfoQueryRequest()));
    }

}
