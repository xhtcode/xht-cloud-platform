package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.module.user.domain.request.SysAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysAdminResponse;
import com.xht.cloud.admin.module.user.service.ISysAdminService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
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
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/admin")
@RequiredArgsConstructor
@Tag(name = "管理员信息")
public class SysAdminController {

    private final ISysAdminService sysAdminService;

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysAdminCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-管理员")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody SysAdminCreateRequest createRequest) {
        sysAdminService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改管理员
     *
     * @param updateRequest {@link SysAdminUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-管理员")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody SysAdminUpdateRequest updateRequest) {
        return ok(sysAdminService.update(updateRequest));
    }

    /**
     * 根据id删除管理员
     *
     * @param ids {@link List <String>} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-管理员")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<Integer> ids) {
        sysAdminService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细管理员
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAdminResponse}
     */
    @Operation(summary = "根据id查询详细-管理员")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysAdminResponse> findById(@PathVariable("id") Integer id) {
        return ok(sysAdminService.findById(id));
    }

    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysAdminQueryRequest}
     * @return {@link SysAdminResponse} 分页详情
     */
    @Operation(summary = "分页查询-管理员")
    @GetMapping
    public R<PageResponse<SysAdminResponse>> findPage(SysAdminQueryRequest queryRequest) {
        return ok(sysAdminService.findPage(queryRequest));
    }

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    @Operation(summary = "修改密码")
    @PutMapping("/reset/password/{userId}")
    public R<Boolean> updatePassWord(@PathVariable("userId") Integer userId, @RequestBody String passWord) {
        return R.ok(sysAdminService.updatePassWord(userId, passWord));
    }
}
