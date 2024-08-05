package com.xht.cloud.generate.module.template.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeGroupResponse;
import com.xht.cloud.generate.module.template.service.IGenCodeGroupService;
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
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/code/group")
@RequiredArgsConstructor
@Tag(name = "模板组")
public class GenCodeGroupController {

    private final IGenCodeGroupService genCodeGroupService;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeGroupCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenCodeGroupCreateRequest createRequest) {
        genCodeGroupService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeGroupUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenCodeGroupUpdateRequest updateRequest) {
        genCodeGroupService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genCodeGroupService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeGroupResponse}
     */
    @Operation(summary = "根据id查询详细-")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<GenCodeGroupResponse> findById(@PathVariable("id") String id) {
        return R.ok(genCodeGroupService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeGroupQueryRequest}
     * @return {@link PageResponse<GenCodeGroupResponse>} 分页详情
     */
    @Operation(summary = "分页查询-")
    @GetMapping
    public R<List<GenCodeGroupResponse>> findPage(GenCodeGroupQueryRequest queryRequest) {
        return R.ok(genCodeGroupService.findPage(queryRequest));
    }

}
