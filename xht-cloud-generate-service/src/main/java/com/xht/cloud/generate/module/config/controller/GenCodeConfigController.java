package com.xht.cloud.generate.module.config.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;
import com.xht.cloud.generate.module.config.service.IGenCodeConfigService;
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
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/code/config")
@RequiredArgsConstructor
@Tag(name = "代码生成器-配置中心")
public class GenCodeConfigController {

    private final IGenCodeConfigService genCodeConfigService;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeConfigCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-配置中心")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenCodeConfigCreateRequest createRequest) {
        genCodeConfigService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeConfigUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-配置中心")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenCodeConfigUpdateRequest updateRequest) {
        genCodeConfigService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-配置中心")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genCodeConfigService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeConfigResponse}
     */
    @GetMapping("/{id}")
    public R<GenCodeConfigResponse> findById(@PathVariable("id") String id) {
        return R.ok(genCodeConfigService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeConfigQueryRequest}
     * @return {@link PageResponse<GenCodeConfigResponse>} 分页详情
     */
    @GetMapping
    public R<PageResponse<GenCodeConfigResponse>> findPage(GenCodeConfigQueryRequest queryRequest) {
        return R.ok(genCodeConfigService.findPage(queryRequest));
    }


    /**
     * 查询全部
     *
     * @return {@link GenCodeConfigResponse}
     */
    @GetMapping("/list/")
    public R<List<GenCodeConfigResponse>> list() {
        return R.ok(genCodeConfigService.list());
    }
}
