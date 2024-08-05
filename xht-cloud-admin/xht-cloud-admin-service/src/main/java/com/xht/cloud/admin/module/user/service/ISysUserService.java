package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.module.user.domain.request.SysUserBaseAddUpdate;
import com.xht.cloud.admin.module.user.domain.request.SysUserProfileRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.UpdatePassWordRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserResponse;
import com.xht.cloud.admin.module.user.domain.response.SysUserVo;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.io.InputStream;
import java.util.List;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
public interface ISysUserService {

    /**
     * 创建
     *
     * @param request {@link SysUserBaseAddUpdate}
     * @return {@link String} 主键
     */
    String create(SysUserBaseAddUpdate request);

    /**
     * 根据id修改
     *
     * @param request {@link SysUserBaseAddUpdate}
     */
    String update(SysUserBaseAddUpdate request);

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserResponse}
     */
    SysUserVo findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link PageResponse<SysUserResponse>} 分页详情
     */
    PageResponse<SysUserResponse> findPage(SysUserQueryRequest queryRequest);

    /**
     * 根据userName查询详细
     *
     * @param userAccount {@link String} 用户账号
     * @return {@link SysUserVo}
     */
    SysUserVo findByUserAccount(String userAccount);

    /**
     * 校验账号是否存在
     *
     * @param userAccount {@link String} 用户账号
     * @return {@link Boolean}
     */
    boolean validationUserAccount(String userAccount);

    /**
     * 修改登录用户信息
     *
     * @param userAccount 用户账号
     * @param request     请求信息
     */
    void updateUserProfile(String userAccount, SysUserProfileRequest request);

    /**
     * 修改当前登录用户密码
     *
     * @param userAccount 用户账号
     * @param request     请求信息
     */
    void updateUserPassword(String userAccount, UpdatePassWordRequest request);

    /**
     * 重置账号密码
     *
     * @param userId 用户id
     */
    String resetUserPassword(String userId);

    /**
     * 修改当前登录用户头像
     *
     * @param userAccount 用户账号
     * @param inputStream 头像io流
     * @return 头像地址
     */
    String updateUserAvatar(String userAccount, InputStream inputStream);

}
