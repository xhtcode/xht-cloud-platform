package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.admin.module.user.service.IUserCenterService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
@Slf4j
@Tag(name = "用户中心")
@RestController
@RequestMapping("/sys/user/center")
@RequiredArgsConstructor
public class UserCenterController {

    private final IUserCenterService userCenterService;

    /**
     * 获取当前登录用户信息
     *
     * @return {@link UserCenterResponse}
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/info")
    public R<UserCenterResponse> findLoginUser() {
        UserCenterResponse loginUserInfo = userCenterService.getLoginUserInfo();
        loginUserInfo.setPassWord(null);
        loginUserInfo.setPassWordSalt(null);
        return ok(loginUserInfo);
    }


    /**
     * 当前登录用户所拥有的菜单路由
     *
     * @return {@link TreeNode}
     */
    @Operation(summary = "当前用户菜单路由")
    @GetMapping(value = "/router")
    public R<List<INode<String>>> getRouters() {
        return ok(userCenterService.getLoginUserRouter());
    }

    /**
     * 修改当前登录的用户信息
     *
     * @param userUpdateRequest 用户要修改的信息
     * @return true修改成功
     */
    @Operation(summary = "修改个人信息")
    @PutMapping(value = "/info")
    public R<Boolean> updateUserInfo(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ok(userCenterService.updateLoginUserInfo(userUpdateRequest));
    }

    /**
     * 修改当前登录的用户头像
     *
     * @param file 头像信息
     * @return true修改成功
     */
    @Operation(summary = "修改个人头像")
    @PutMapping(value = "/avatar")
    public R<Boolean> updateUserAvatar(@RequestPart("file") MultipartFile file) {
        return ok(userCenterService.updateLoginUserAvatar(file));
    }


}
