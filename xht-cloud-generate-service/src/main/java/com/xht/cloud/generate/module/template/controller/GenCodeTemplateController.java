package com.xht.cloud.generate.module.template.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeTemplateResponse;
import com.xht.cloud.generate.module.template.service.IGenCodeTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/code/template")
@RequiredArgsConstructor
@Tag(name = "代码生成器-代码模板")
public class GenCodeTemplateController {

    private final IGenCodeTemplateService genCodeTemplateService;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeTemplateCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-代码模板")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenCodeTemplateCreateRequest createRequest) {
        genCodeTemplateService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeTemplateUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-代码模板")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenCodeTemplateUpdateRequest updateRequest) {
        genCodeTemplateService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-代码模板")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genCodeTemplateService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeTemplateResponse}
     */
    @Operation(summary = "根据id查询详细-代码生成器-代码模板")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenCodeTemplateResponse> findById(@PathVariable("id") String id) {
        return ok(genCodeTemplateService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeTemplateQueryRequest}
     * @return {@link PageResponse<GenCodeTemplateResponse>} 分页详情
     */
    @Operation(summary = "分页查询-代码生成器-代码模板")
    @GetMapping
    public R<PageResponse<GenCodeTemplateResponse>> findPage(GenCodeTemplateQueryRequest queryRequest) {
        return ok(genCodeTemplateService.findPage(queryRequest));
    }

}
