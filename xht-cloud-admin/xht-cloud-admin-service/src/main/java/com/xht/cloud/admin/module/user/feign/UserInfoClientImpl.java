package com.xht.cloud.admin.module.user.feign;

import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.api.user.feign.UserInfoClient;
import com.xht.cloud.admin.module.user.domain.response.SysUserVo;
import com.xht.cloud.admin.module.user.service.ISysUserService;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：用户信息查询内部专用
 *
 * @author 小糊涂
 **/
@Tag(name = "用户信息查询（内部专用）")
@RestController
@RequiredArgsConstructor
public class UserInfoClientImpl implements UserInfoClient {

    private final ISysUserService sysUserService;

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return {@link SysUserResDTO} 用户信息
     */
    @Override
    @SkipAuthentication
    @Operation(summary = "根据用户ID查询用户")
    public R<SysUserResDTO> findUserById(String userId) {
        SysUserVo sysUserVo = sysUserService.findById(userId);
        return R.ok(sysUserVo.toDTO());
    }

    /**
     * 根据用户账号查询用户
     *
     * @param userAccount 用户账号
     * @return {@link SysUserResDTO} 用户信息
     */
    @Override
    @SkipAuthentication
    @Operation(summary = "根据用户账号查询用户")
    public R<SysUserResDTO> findUserByAccount(String userAccount) {
        SysUserVo sysUserVo = sysUserService.findByUserAccount(userAccount);
        return R.ok(sysUserVo.toDTO());
    }
}
