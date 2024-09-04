package com.xht.cloud.admin.module.dict.controller;

import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeVo;
import com.xht.cloud.admin.module.dict.service.ISysDictTypeService;
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
 * 描述 ：字典类型
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
@Tag(name = "字典类型")
public class SysDictTypeController {

    private final ISysDictTypeService sysDictTypeService;

    /**
     * 创建
     *
     * @param createRequest {@link SysDictTypeCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-字典类型")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict:add')")
    public R<Boolean> create(@Validated @RequestBody SysDictTypeCreateRequest createRequest) {
        sysDictTypeService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysDictTypeUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-字典类型")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict:edit')")
    public R<Boolean> update(@Validated @RequestBody SysDictTypeUpdateRequest updateRequest) {
        sysDictTypeService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除字典类型
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-字典类型")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysDictTypeService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询字典类型详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictTypeResponse}
     */
    @Operation(summary = "根据id查询详细-字典类型")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysDictTypeResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysDictTypeService.findById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param queryRequest {@link SysDictTypeQueryRequest}
     * @return {@link SysDictTypeResponse} 分页详情
     */
    @Operation(summary = "分页查询-字典类型")
    @GetMapping
    public R<PageResponse<SysDictTypeResponse>> findPage(SysDictTypeQueryRequest queryRequest) {
        return R.ok(sysDictTypeService.findPage(queryRequest));
    }

    /**
     * 根据 dictType 字典类型查询详细
     *
     * @param dictType {@link String} 字典类型
     * @return {@link SysDictTypeVo}
     */
    @Operation(summary = "根据id查询详细-字典类型")
    @Parameter(name = "dictType", description = "字典类型", required = true)
    @GetMapping("/code/{dictType}")
    public R<SysDictTypeVo> findByDictType(@PathVariable("dictType") String dictType) {
        return ok(sysDictTypeService.findByDictType(dictType));
    }

}
