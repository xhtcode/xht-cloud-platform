package com.xht.cloud.generate.module.table.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.table.domain.request.GenTableCreateRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableUpdateRequest;
import com.xht.cloud.generate.module.table.domain.request.ImportRequest;
import com.xht.cloud.generate.module.table.domain.response.GenTableResponse;
import com.xht.cloud.generate.module.table.domain.response.GenerateVo;
import com.xht.cloud.generate.module.table.service.IGenTableService;
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
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/table")
@RequiredArgsConstructor
@Tag(name = "代码生成器-数据库信息")
public class GenTableController {

    private final IGenTableService genTableService;

    /**
     * 创建
     *
     * @param createRequest {@link GenTableCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-数据库信息")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenTableCreateRequest createRequest) {
        genTableService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenTableUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-数据库信息")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenTableUpdateRequest updateRequest) {
        genTableService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-数据库信息")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genTableService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenerateVo}
     */
    @Operation(summary = "根据id查询详细-代码生成器-数据库信息")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenerateVo> findById(@PathVariable("id") String id) {
        return R.ok(genTableService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableQueryRequest}
     * @return {@link GenTableResponse} 分页详情
     */
    @Operation(summary = "分页查询-代码生成器-数据库信息")
    @GetMapping
    public R<PageResponse<GenTableResponse>> findPage(GenTableQueryRequest queryRequest) {
        return R.ok(genTableService.findPage(queryRequest));
    }


    /**
     * 与数据库中表进行同步
     *
     * @param request 表名
     * @return R
     */
    @PostMapping("/importTable")
    public R<Boolean> importTableSave(@RequestBody ImportRequest request) {
        return R.ok(genTableService.importTable(request));
    }

    /**
     * 单表 ====与数据库中表进行同步
     *
     * @param tableId 表id
     * @return R
     */
    @PostMapping("/syncDb/{tableId}")
    public R<Boolean> syncDb(@PathVariable("tableId") String tableId) {
        return R.ok(genTableService.synchronous(tableId));
    }


    /**
     * 获取未进行同步的表
     *
     * @param importRequest 表名
     * @return R
     */
    @GetMapping("/syncList/")
    public R<List<GenTableResponse>> syncList(ImportRequest importRequest) {
        return R.ok(genTableService.syncList(importRequest));
    }

}
