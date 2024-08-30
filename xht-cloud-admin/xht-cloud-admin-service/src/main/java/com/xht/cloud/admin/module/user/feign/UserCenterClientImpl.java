package com.xht.cloud.admin.module.user.feign;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.api.user.feign.UserCenterClient;
import com.xht.cloud.admin.module.user.service.IUserCenterService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
@Tag(name = "用户中心(内部专用)")
@RestController
@RequiredArgsConstructor
public class UserCenterClientImpl implements UserCenterClient {

    private final IUserCenterService userCenterService;


    /**
     * 根据用户ID 用户类型 查询用户信息
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @return {@link UserCenterResponse} 用户信息
     */
    @Override
    @SkipAuthentication
    public R<UserCenterResponse> findUserById(String userId, UserTypeEnums userType) {
        return R.ok(userCenterService.getUserInfoByUserId(userId, userType));
    }

    /**
     * 根据用户账号 用户类型 查询用户信息
     *
     * @param userName 用户账号
     * @param userType 用户类型
     * @return {@link UserCenterResponse} 用户信息
     */
    @Override
    @SkipAuthentication
    public R<UserCenterResponse> findUserByUserName(String userName, UserTypeEnums userType) {
        return R.ok(userCenterService.getUserInfoByUserName(userName, userType));
    }

}
