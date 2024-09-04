package com.xht.cloud.admin.module.dept.controller;

import com.xht.cloud.admin.module.dept.domain.request.SysDeptCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.admin.module.dept.service.ISysDeptService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
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

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/dept")
@RequiredArgsConstructor
@Tag(name = "部门")
public class SysDeptController {

    private final ISysDeptService sysDeptService;

    /**
     * 创建
     *
     * @param createRequest {@link SysDeptCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-部门")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dept:add')")
    public R<Boolean> create(@Validated @RequestBody SysDeptCreateRequest createRequest) throws Exception {
        sysDeptService.validate(createRequest);
        sysDeptService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysDeptUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-部门")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dept:edit')")
    public R<Boolean> update(@Validated @RequestBody SysDeptUpdateRequest updateRequest) throws Exception {
        sysDeptService.validate(updateRequest);
        sysDeptService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-部门")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dept:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysDeptService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDeptResponse}
     */
    @Operation(summary = "根据id查询详细-部门")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysDeptResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysDeptService.findById(id));
    }

    /**
     * 查询树
     *
     * @param queryRequest {@link SysDeptQueryRequest}
     * @return {@link SysDeptResponse} 分页详情
     */
    @Operation(summary = "查询树-部门")
    @GetMapping
    public R<List<INode<String>>> findTree(SysDeptQueryRequest queryRequest) {
        List<SysDeptResponse> list = sysDeptService.findList(queryRequest);
        return R.ok(sysDeptService.convert(list));
    }


}
