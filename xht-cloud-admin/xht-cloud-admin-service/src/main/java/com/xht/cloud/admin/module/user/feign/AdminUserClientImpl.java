package com.xht.cloud.admin.module.user.feign;

import com.xht.cloud.admin.api.user.dto.SysAdminUserResDTO;
import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.api.user.feign.AdminUserClient;
import com.xht.cloud.admin.module.user.domain.response.SysAdminResponse;
import com.xht.cloud.admin.module.user.service.ISysAdminService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 描述 ：管理员信息
 *
 * @author : 小糊涂
 **/
@Tag(name = "管理员信息（内部专用）")
@RestController
@RequiredArgsConstructor
public class AdminUserClientImpl implements AdminUserClient {

    private final ISysAdminService sysAdminService;

    /**
     * 根据管理员ID查询管理员
     *
     * @param userId 管理员ID
     * @return {@link SysUserResDTO} 管理员信息
     */
    @Override
    @Operation(summary = "根据管理员ID查询管理员")
    @SkipAuthentication
    public R<SysAdminUserResDTO> findUserById(Integer userId) {
        return R.ok(convert(sysAdminService.findById(userId)));
    }

    /**
     * 根据管理员账号查询管理员
     *
     * @param userName 管理员账号
     * @return {@link SysUserResDTO} 管理员信息
     */

    @Override
    @Operation(summary = "根据管理员账号查询管理员")
    @SkipAuthentication
    public R<SysAdminUserResDTO> findUserByAccount(String userName) {
        return R.ok(convert(sysAdminService.findUserByAccount(userName)));
    }

    private SysAdminUserResDTO convert(SysAdminResponse adminResponse) {
        if (Objects.isNull(adminResponse)) return null;
        return adminResponse.convert();
    }
}
