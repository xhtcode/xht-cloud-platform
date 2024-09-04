package com.xht.cloud.admin.module.dict.controller;

import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.exceptions.DictException;
import com.xht.cloud.admin.module.dict.service.ISysDictItemService;
import com.xht.cloud.admin.module.dict.service.ISysDictTypeService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.web.validation.group.Query;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/dict/item")
@RequiredArgsConstructor
@Tag(name = "字典数据")
public class SysDictItemController {

    private final ISysDictItemService sysDictItemService;

    private final ISysDictTypeService sysDictService;

    /**
     * 创建字典数据
     *
     * @param createRequest {@link SysDictItemCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-字典数据")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict-item:add')")
    public R<Boolean> create(@Validated @RequestBody SysDictItemCreateRequest createRequest) {
        SysDictTypeResponse byId = sysDictService.findById(createRequest.getDictId());
        if (Objects.isNull(byId)) {
            throw new DictException("上级字典不存在！");
        }
        sysDictItemService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改字典数据
     *
     * @param updateRequest {@link SysDictItemUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-字典数据")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict-item:edit')")
    public R<Boolean> update(@Validated @RequestBody SysDictItemUpdateRequest updateRequest) {
        SysDictTypeResponse byId = sysDictService.findById(updateRequest.getDictId());
        if (Objects.isNull(byId)) {
            throw new DictException("上级字典不存在或未启用！");
        }
        sysDictItemService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除字典数据
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-字典数据")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:dict-item:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysDictItemService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询字典数据详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    @Operation(summary = "根据id查询详细-字典数据")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysDictItemResponse> findById(@PathVariable("id") String id) {
        return R.ok(sysDictItemService.findById(id));
    }

    /**
     * 分页查询字典数据
     *
     * @param queryRequest {@link SysDictItemQueryRequest}
     * @return {@link SysDictItemResponse} 分页详情
     */
    @Operation(summary = "分页查询-字典数据")
    @GetMapping
    public R<PageResponse<SysDictItemResponse>> findPage(@Validated(Query.class) SysDictItemQueryRequest queryRequest) {
        return R.ok(sysDictItemService.findPage(queryRequest));
    }

}
