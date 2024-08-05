package com.xht.cloud.generate.module.entity.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassCreateRequest;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassQueryRequest;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassUpdateRequest;
import com.xht.cloud.generate.module.entity.domain.response.GenCodeBaseClassResponse;
import com.xht.cloud.generate.module.entity.service.IGenCodeBaseClassService;
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
 * 描述 ：代码生成器-基类
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/code/base/class")
@RequiredArgsConstructor
@Tag(name = "代码生成器-基类")
public class GenCodeBaseClassController {

    private final IGenCodeBaseClassService genCodeBaseClassService;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeBaseClassCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-基类")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenCodeBaseClassCreateRequest createRequest) {
        genCodeBaseClassService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeBaseClassUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-基类")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenCodeBaseClassUpdateRequest updateRequest) {
        genCodeBaseClassService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-基类")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genCodeBaseClassService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeBaseClassResponse}
     */
    @Operation(summary = "根据id查询详细-代码生成器-基类")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenCodeBaseClassResponse> findById(@PathVariable("id") String id) {
        return R.ok(genCodeBaseClassService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeBaseClassQueryRequest}
     * @return {@link PageResponse<GenCodeBaseClassResponse>} 分页详情
     */
    @Operation(summary = "分页查询-代码生成器-基类")
    @GetMapping
    public R<PageResponse<GenCodeBaseClassResponse>> findPage(GenCodeBaseClassQueryRequest queryRequest) {
        return R.ok(genCodeBaseClassService.findPage(queryRequest));
    }

}
