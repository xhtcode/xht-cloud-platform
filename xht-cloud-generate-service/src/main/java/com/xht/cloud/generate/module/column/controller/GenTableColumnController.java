package com.xht.cloud.generate.module.column.controller;

import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnCreateRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnQueryRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.domain.response.GenTableColumnResponse;
import com.xht.cloud.generate.module.column.service.IGenTableColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/table/column")
@RequiredArgsConstructor
@Tag(name = "代码生成业务字段")
public class GenTableColumnController {

    private final IGenTableColumnService genTableColumnService;

    /**
     * 创建
     *
     * @param createRequest {@link GenTableColumnCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成业务字段")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenTableColumnCreateRequest createRequest) {
        genTableColumnService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenTableColumnUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成业务字段")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenTableColumnUpdateRequest updateRequest) {
        genTableColumnService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成业务字段")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genTableColumnService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenTableColumnResponse}
     */
    @Operation(summary = "根据id查询详细-代码生成业务字段")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenTableColumnResponse> findById(@PathVariable("id") String id) {
        return R.ok(genTableColumnService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableColumnQueryRequest}
     * @return {@link GenTableColumnResponse} 分页详情
     */
    @Operation(summary = "分页查询-代码生成业务字段")
    @GetMapping
    public R<PageResponse<GenTableColumnResponse>> findPage(GenTableColumnQueryRequest queryRequest) {
        return R.ok(genTableColumnService.findPage(queryRequest));
    }

}
