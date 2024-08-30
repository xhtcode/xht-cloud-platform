package com.xht.cloud.admin.module.user.service.impl;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.admin.module.user.factory.UserInfoFactory;
import com.xht.cloud.admin.module.user.factory.UserRouterFactory;
import com.xht.cloud.admin.module.user.factory.UserUpdateFactory;
import com.xht.cloud.admin.module.user.service.IUserCenterService;
import com.xht.cloud.framework.utils.treenode.INode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCenterServiceImpl implements IUserCenterService {

    /**
     * 根据用户账号获取用户信息
     *
     * @param userAccount 用户账号
     * @param userType    用户类型
     * @return 用户信息
     */
    @Override
    public UserCenterResponse getUserInfoByUserName(String userAccount, UserTypeEnums userType) {
        return UserInfoFactory.getStrategyNoNull(userType).getUserInfoByUserName(userAccount);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @return 用户信息
     */
    @Override
    public UserCenterResponse getUserInfoByUserId(String userId, UserTypeEnums userType) {
        return UserInfoFactory.getStrategyNoNull(userType).getUserInfoByUserId(userId);
    }

    /**
     * 获取用户菜单路由
     *
     * @param userAccount 用户账号
     * @param userType    用户类型
     * @return 用户信息
     */
    @Override
    public List<INode<String>> getUserRouter(String userAccount, UserTypeEnums userType) {
        return UserRouterFactory.getStrategyNoNull(userType).getRouter(userAccount);
    }

    /**
     * 根据用户id用户类型 修改用户信息
     *
     * @param userId            用户id
     * @param userType          用户类型
     * @param userUpdateRequest 用户要修改的信息
     * @return true修改成功
     */
    @Override
    public Boolean updateUserInfo(String userId, UserTypeEnums userType, UserUpdateRequest userUpdateRequest) {
        return UserUpdateFactory.getStrategyNoNull(userType).updateUserInfo(userId, userUpdateRequest);
    }

    /**
     * 根据用户id用户类型 修改用户头像
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @param file     头像信息
     * @return true修改成功
     */
    @Override
    public Boolean updateUserAvatar(String userId, UserTypeEnums userType, MultipartFile file) {
        return UserUpdateFactory.getStrategyNoNull(userType).updateUserAvatar(userId, file);
    }

}
