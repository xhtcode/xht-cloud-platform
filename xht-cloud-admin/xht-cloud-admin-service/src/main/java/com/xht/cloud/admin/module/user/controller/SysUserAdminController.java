package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.module.user.domain.request.SysUserAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserAdminResponse;
import com.xht.cloud.admin.module.user.service.ISysUserAdminService;
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
@RequestMapping("/sys/user/admin")
@RequiredArgsConstructor
@Tag(name = "管理员")
public class SysUserAdminController {

    private final ISysUserAdminService sysAdminService;

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysUserAdminCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-管理员")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody SysUserAdminCreateRequest createRequest) {
        sysAdminService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改管理员
     *
     * @param updateRequest {@link SysUserAdminUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-管理员")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody SysUserAdminUpdateRequest updateRequest) {
        return ok(sysAdminService.update(updateRequest));
    }

    /**
     * 根据id删除管理员
     *
     * @param ids {@link Integer} id集合
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
     * @return {@link SysUserAdminResponse}
     */
    @Operation(summary = "根据id查询详细-管理员")
    @Parameter(name = "id", description = "id", required = true)
    @GetMapping("/{id}")
    public R<SysUserAdminResponse> findById(@PathVariable("id") Integer id) {
        return ok(sysAdminService.findById(id));
    }

    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysUserAdminQueryRequest}
     * @return {@link SysUserAdminResponse} 分页详情
     */
    @Operation(summary = "分页查询-管理员")
    @GetMapping
    public R<PageResponse<SysUserAdminResponse>> findPage(SysUserAdminQueryRequest queryRequest) {
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
