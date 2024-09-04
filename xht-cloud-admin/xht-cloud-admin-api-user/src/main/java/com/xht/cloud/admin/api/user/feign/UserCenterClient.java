package com.xht.cloud.admin.api.user.feign;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
@FeignClient(value = "xht-cloud-admin-service", contextId = "userCenterClient")
public interface UserCenterClient {


    /**
     * 根据用户ID 用户类型 查询用户信息
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @return {@link UserCenterResponse} 用户信息
     */
    @GetMapping("/api/sys/user/center/id/{userId}/{userType}")
    R<UserCenterResponse> findUserById(@PathVariable("userId") String userId, @PathVariable("userType") UserTypeEnums userType);


    /**
     * 根据用户账号 用户类型 查询用户信息
     *
     * @param userName 用户账号
     * @param userType 用户类型
     * @return {@link UserCenterResponse} 用户信息
     */
    @GetMapping("/api/sys/user/center/name/{userName}/{userType}")
    R<UserCenterResponse> findUserByUserName(@PathVariable("userName") String userName, @PathVariable("userType") UserTypeEnums userType);

}
