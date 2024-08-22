package com.xht.cloud.admin.module.sequence.controller;

import com.xht.cloud.admin.module.sequence.domain.request.IdRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceCreateRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceQueryRequest;
import com.xht.cloud.admin.module.sequence.domain.request.SysSequenceUpdateRequest;
import com.xht.cloud.admin.module.sequence.domain.response.SysSequenceResponse;
import com.xht.cloud.admin.enums.GenerateIdType;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.generate.GenerateIdFactory;
import com.xht.cloud.admin.module.sequence.service.ISysSequenceService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：序列生成器管理
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/sequence")
@RequiredArgsConstructor
@Tag(name = "系统配置信息")
public class SysSequenceController {


    private final ISysSequenceService sysSequenceService;

    /**
     * 创建
     *
     * @param createRequest {@link SysSequenceCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-系统配置信息")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:sequence:add')")
    public R<Boolean> create(@Validated @RequestBody SysSequenceCreateRequest createRequest) {
        sysSequenceService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysSequenceUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-序列生成器")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:sequence:edit')")
    public R<Boolean> update(@Validated @RequestBody SysSequenceUpdateRequest updateRequest) {
        sysSequenceService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-序列生成器")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('sys:sequence:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        sysSequenceService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysSequenceResponse}
     */
    @Operation(summary = "根据id查询详细-序列生成器")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysSequenceResponse> findById(@PathVariable("id") String id) {
        return ok(sysSequenceService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysSequenceQueryRequest}
     * @return {@link SysSequenceResponse} 分页详情
     */
    @Operation(summary = "分页查询-序列生成器")
    @GetMapping
    public R<PageResponse<SysSequenceResponse>> findPage(SysSequenceQueryRequest queryRequest) {
        return ok(sysSequenceService.findPage(queryRequest));
    }

    /**
     * 序列生成
     *
     * @param request {@link IdRequest}
     */
    @SkipAuthentication
    @Operation(summary = "序列生成")
    @PostMapping("/generate")
    public R<String> generate(@Validated @RequestBody IdRequest request) {
        GenerateIdType generateIdType = GenerateIdType.generateIdType(request.getCode());
        return R.ok(GenerateIdFactory.
                getHandler(generateIdType).orElseThrow(() -> new SequenceException("找不到系列类型"))
                .generate(request));
    }
}
