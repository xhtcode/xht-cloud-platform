package com.xht.cloud.admin.api.user.feign;

import com.xht.cloud.admin.api.user.dto.SysAdminUserResDTO;
import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.server.ServerConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 描述 ：管理员信息
 *
 * @author : 小糊涂
 **/
@FeignClient(value = ServerConstants.XHT_CLOUD_ADMIN, contextId = "adminUserClient")
public interface AdminUserClient {

    /**
     * 根据管理员ID查询管理员
     *
     * @param userId 管理员ID
     * @return {@link SysUserResDTO} 管理员信息
     */
    @GetMapping("/api/sys/admin/{userId}/")
    R<SysAdminUserResDTO> findUserById(@PathVariable("userId") Integer userId);


    /**
     * 根据管理员账号查询管理员
     *
     * @param userName 管理员账号
     * @return {@link SysUserResDTO} 管理员信息
     */
    @GetMapping("/api/sys/admin/{userName}")
    R<SysAdminUserResDTO> findUserByAccount(@PathVariable("userName") String userName);

}
