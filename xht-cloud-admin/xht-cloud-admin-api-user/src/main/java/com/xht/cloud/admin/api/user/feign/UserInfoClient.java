package com.xht.cloud.admin.api.user.feign;

import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.framework.core.server.ServerConstants;
import com.xht.cloud.framework.core.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 描述 ： 用户信息
 *
 * @author 小糊涂
 **/
@FeignClient(value = ServerConstants.XHT_CLOUD_ADMIN, contextId = "userInfoApi")
public interface UserInfoClient {

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return {@link SysUserResDTO} 用户信息
     */
    @GetMapping("/api/sys/user/info/{userId}/")
    R<SysUserResDTO> findUserById(@PathVariable("userId") String userId);


    /**
     * 根据用户账号查询用户
     *
     * @param userAccount 用户账号
     * @return {@link SysUserResDTO} 用户信息
     */
    @GetMapping("/api/sys/user/info/{userAccount}")
    R<SysUserResDTO> findUserByAccount(@PathVariable("userAccount") String userAccount);

}
