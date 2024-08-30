package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
public interface IUserCenterService {

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    default UserCenterResponse getLoginUserInfo() {
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("用户登录信息获取失败"));
        return getUserInfoByUserName(userDetailsBO.getNickName(), userDetailsBO.getUserType());
    }

    /**
     * 根据用户账号获取用户信息
     *
     * @param userAccount 用户账号
     * @param userType    用户类型
     * @return 用户信息
     */
    UserCenterResponse getUserInfoByUserName(String userAccount, UserTypeEnums userType);


    /**
     * 根据用户id获取用户信息
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @return 用户信息
     */
    UserCenterResponse getUserInfoByUserId(String userId, UserTypeEnums userType);


    /**
     * 当前登录用户所拥有的菜单路由
     *
     * @return {@link TreeNode}
     */
    default List<INode<String>> getLoginUserRouter() {
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("用户登录信息获取失败"));
        return getUserRouter(userDetailsBO.getNickName(), userDetailsBO.getUserType());
    }

    /**
     * 获取用户菜单路由
     *
     * @param userAccount 用户账号
     * @param userType    用户类型
     * @return 用户信息
     */
    List<INode<String>> getUserRouter(String userAccount, UserTypeEnums userType);

    /**
     * 修改当前登录的用户信息
     *
     * @param userUpdateRequest 用户要修改的信息
     * @return true修改成功
     */
    default Boolean updateLoginUserInfo(UserUpdateRequest userUpdateRequest) {
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("用户登录信息获取失败"));
        return updateUserInfo(userDetailsBO.getId(), userDetailsBO.getUserType(), userUpdateRequest);
    }

    /**
     * 根据用户id用户类型 修改用户信息
     *
     * @param userId            用户id
     * @param userType          用户类型
     * @param userUpdateRequest 用户要修改的信息
     * @return true修改成功
     */
    Boolean updateUserInfo(String userId, UserTypeEnums userType, UserUpdateRequest userUpdateRequest);

    /**
     * 修改当前登录的用户头像
     *
     * @param file 头像信息
     * @return true修改成功
     */
    default Boolean updateLoginUserAvatar(MultipartFile file) {
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("用户登录信息获取失败"));
        return updateUserAvatar(userDetailsBO.getId(), userDetailsBO.getUserType(), file);
    }

    /**
     * 根据用户id用户类型 修改用户头像
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @param file     头像信息
     * @return true修改成功
     */
    Boolean updateUserAvatar(String userId, UserTypeEnums userType, MultipartFile file);

}
