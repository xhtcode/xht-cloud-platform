package com.xht.cloud.admin.module.org.controller;

import cn.hutool.core.lang.tree.Tree;
import com.xht.cloud.admin.module.org.domain.request.SysOrgCreateRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgQueryRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgUpdateRequest;
import com.xht.cloud.admin.module.org.domain.response.SysOrgResponse;
import com.xht.cloud.admin.module.org.service.ISysOrgService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.domain.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Tag(name = "组织机构", description = "sys")
@RestController
@RequestMapping("/sys/org")
@RequiredArgsConstructor
public class SysOrgController {

    private final ISysOrgService sysOrgService;

    /**
     * 新增组织机构
     *
     * @param createRequest {@link SysOrgCreateRequest} 创建参数
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "新增")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody SysOrgCreateRequest createRequest) {
        return ok(sysOrgService.create(createRequest));
    }

    /**
     * 根据主键修改组织机构
     *
     * @param updateRequest {@link SysOrgUpdateRequest} 修改参数
     * @return {@linkplain Boolean} true成功 false失败
     */
    @Operation(summary = "修改")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody SysOrgUpdateRequest updateRequest) {
        return ok(sysOrgService.update(updateRequest));
    }

    /**
     * 根据主键删除{serviceDesc}
     *
     * @param id 主键
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public R<Boolean> remove(@PathVariable("id") Long id) {
        return ok(sysOrgService.remove(id));
    }

    /**
     * 根据主键批量删除{serviceDesc}
     *
     * @param ids id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "批量删除")
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<Long> ids) {
        return ok(sysOrgService.removeBatch(ids));
    }

    /**
     * 根据主键查询组织机构详细
     *
     * @param id 数据库主键
     * @return {@link SysOrgResponse} 文件详细
     */
    @Operation(summary = "查询详细")
    @GetMapping("/detail/{id}")
    public R<SysOrgResponse> findById(@PathVariable("id") Long id) {
        return R.ok(sysOrgService.findById(id));
    }

    /**
     * 分页查询组织机构
     *
     * @param queryRequest {@link SysOrgQueryRequest} 查询参数
     * @return 分页详情
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public R<PageResponse<SysOrgResponse>> findPage(@Validated SysOrgQueryRequest queryRequest) {
        return R.ok(sysOrgService.findPage(queryRequest));
    }

    @GetMapping("/tree")
    public R<List<Tree<Long>>> tree() {
        return R.ok(sysOrgService.tree());
    }
}
