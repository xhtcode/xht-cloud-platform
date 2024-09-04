package com.xht.cloud.generate.module.database.controller;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseCreateRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseQueryRequest;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseUpdateRequest;
import com.xht.cloud.generate.module.database.domain.response.GenDatabaseResponse;
import com.xht.cloud.generate.module.database.service.IGenDatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 描述 ：代码生成器-数据源管理
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/database")
@RequiredArgsConstructor
@Tag(name = "代码生成器-数据源管理")
public class GenDatabaseController {

    private final IGenDatabaseService genDatabaseService;

    /**
     * 创建
     *
     * @param createRequest {@link GenDatabaseCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-数据源管理")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenDatabaseCreateRequest createRequest) {
        genDatabaseService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenDatabaseUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-数据源管理")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenDatabaseUpdateRequest updateRequest) {
        genDatabaseService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-数据源管理")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genDatabaseService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenDatabaseResponse}
     */
    @Operation(summary = "根据id查询详细-代码生成器-数据源管理")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenDatabaseResponse> findById(@PathVariable("id") String id) {
        return R.ok(genDatabaseService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link GenDatabaseResponse} 分页详情
     */
    @Operation(summary = "分页查询-代码生成器-数据源管理")
    @GetMapping
    public R<PageResponse<GenDatabaseResponse>> findPage(GenDatabaseQueryRequest queryRequest) {
        return R.ok(genDatabaseService.findPage(queryRequest));
    }

    /**
     * 查询集合
     *
     * @param queryRequest {@link GenDatabaseQueryRequest}
     * @return {@link GenDatabaseResponse} 分页详情
     */
    @Operation(summary = "查询集合-代码生成器-数据源管理")
    @GetMapping("/list/")
    public R<List<GenDatabaseResponse>> list(GenDatabaseQueryRequest queryRequest) {
        return R.ok(genDatabaseService.list(queryRequest));
    }


}
