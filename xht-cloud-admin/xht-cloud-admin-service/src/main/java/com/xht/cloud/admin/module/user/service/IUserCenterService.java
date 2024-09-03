package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;

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
     * 获取登录日志
     *
     * @param maxSize 最大记录数 超过10取10 小于5取5
     * @return 登录日志
     */
    List<SysLoginLogResponse> getUserLoginLog(int maxSize);
}
