package com.xht.cloud.admin.module.oauth2.controller;

import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientCreateRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientQueryRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientUpdateRequest;
import com.xht.cloud.admin.module.oauth2.domain.response.Oauth2RegisteredClientResponse;
import com.xht.cloud.admin.module.oauth2.service.IOauth2RegisteredClientService;
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
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/oauth2/registered/client")
@RequiredArgsConstructor
@Tag(name = "oauth2 客户端信息")
public class Oauth2RegisteredClientController {

    private final IOauth2RegisteredClientService oauth2RegisteredClientService;

    /**
     * 创建
     *
     * @param createRequest {@link Oauth2RegisteredClientCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-oauth2 客户端信息")
    @PostMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('oauth2:registered-client:add')")
    public R<Boolean> create(@Validated @RequestBody Oauth2RegisteredClientCreateRequest createRequest) {
        oauth2RegisteredClientService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link Oauth2RegisteredClientUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-oauth2 客户端信息")
    @PutMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('oauth2:registered-client:edit')")
    public R<Boolean> update(@Validated @RequestBody Oauth2RegisteredClientUpdateRequest updateRequest) {
        oauth2RegisteredClientService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-oauth2 客户端信息")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    @Idempotent
    @PreAuthorize("@oauth2.hasAnyAuthority('oauth2:registered-client:remove')")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        oauth2RegisteredClientService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link Oauth2RegisteredClientResponse}
     */
    @Operation(summary = "根据id查询详细-oauth2 客户端信息")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    @SkipAuthentication
    public R<Oauth2RegisteredClientResponse> findById(@PathVariable("id") String id) {
        return R.ok(oauth2RegisteredClientService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link Oauth2RegisteredClientQueryRequest}
     * @return {@link Oauth2RegisteredClientResponse} 分页详情
     */
    @Operation(summary = "分页查询-oauth2 客户端信息")
    @GetMapping
    public R<PageResponse<Oauth2RegisteredClientResponse>> findPage(Oauth2RegisteredClientQueryRequest queryRequest) {
        return R.ok(oauth2RegisteredClientService.findPage(queryRequest));
    }

}
