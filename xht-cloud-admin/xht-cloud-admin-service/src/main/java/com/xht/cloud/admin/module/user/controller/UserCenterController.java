package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.admin.module.user.domain.PassWordBO;
import com.xht.cloud.admin.module.user.service.IUserCenterService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 获取登录日志
     *
     * @param maxSize 最大记录数 超过10取10 小于5取5
     * @return 登录日志
     */
    @Operation(summary = "获取登录日志", description = "最大记录数 超过10取10 小于5取5")
    @GetMapping(value = "/login/log")
    public R<List<SysLoginLogResponse>> getUserLoginLog(@RequestParam(value = "maxSize", required = false, defaultValue = "5") int maxSize) {
        return ok(userCenterService.getUserLoginLog(maxSize));
    }

    /**
     * 用户修改密码
     *
     * @param updatePassWordBO 密码请求
     * @return true 修改成功
     */
    @Operation(summary = "用户修改密码")
    @PutMapping("/pwd")
    public R<Boolean> updatePassword(@Validated @RequestBody PassWordBO updatePassWordBO) {
        return ok(Boolean.TRUE);
    }

}
