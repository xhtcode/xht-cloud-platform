package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.admin.module.permissions.domain.response.SysMenuResponse;
import com.xht.cloud.admin.module.permissions.service.ISysMenuService;
import com.xht.cloud.admin.module.user.domain.request.SysUserProfileRequest;
import com.xht.cloud.admin.module.user.domain.request.UpdatePassWordRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserVo;
import com.xht.cloud.admin.module.user.service.ISysUserService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/user/profile")
@RequiredArgsConstructor
@Tag(name = "用户信息查询")
public class SysUserProfileController {

    private final ISysUserService sysUserService;

    private final ISysMenuService sysMenuService;

    /**
     * 查询当前登录用户信息
     *
     * @return {@link SysUserVo}
     */
    @Operation(summary = "查询当前登录用户信息")
    @GetMapping("/info")
    public R<SysUserVo> findLoginUser() {
        SysUserVo sysUserVo = sysUserService.findByUserAccount(SecurityContextUtil.getUserAccount());
        if (Objects.nonNull(sysUserVo) && Objects.nonNull(sysUserVo.getSysUser())) {
            sysUserVo.getSysUser().setPassWord(null);
            sysUserVo.getSysUser().setPassWordSalt(null);
        }
        return R.ok(sysUserVo);
    }

    /**
     * 当前登录用户所拥有的菜单路由
     *
     * @return {@link TreeNode}
     */
    @Operation(summary = "当前用户菜单路由")
    @GetMapping(value = "/getRouters")
    public R<List<INode<String>>> getRouters() {
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("用户未登录！"));
        List<SysMenuResponse> sysMenuResponses = sysMenuService.selectByUserId(userDetailsBO.getId(),
                new MenuTypeEnums[]{MenuTypeEnums.M, MenuTypeEnums.C});
        return R.ok(sysMenuService.convert(sysMenuResponses, Boolean.TRUE));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public R<Boolean> updateUserProfile(@Valid @RequestBody SysUserProfileRequest request) {
        sysUserService.updateUserProfile(SecurityContextUtil.getUserAccount(), request);
        return R.ok(true);
    }

    @PutMapping("/update/password")
    @Operation(summary = "修改用户个人密码")
    public R<Boolean> updateUserProfilePassword(@Valid @RequestBody UpdatePassWordRequest request) {
        sysUserService.updateUserPassword(SecurityContextUtil.getUserAccount(), request);
        return R.ok(true);
    }

    @RequestMapping(value = "/update/avatar", method = {RequestMethod.PUT})
    @Operation(summary = "上传用户个人头像")
    public R<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
      //  Assert.isTrue(file.isEmpty(), () -> new FileException(FileErrorStatusCode.FILE_NOT_EXISTS));
        String avatar = sysUserService.updateUserAvatar(SecurityContextUtil.getUserAccount(), file.getInputStream());
        return R.ok(avatar);
    }

}
