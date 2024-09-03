package com.xht.cloud.admin.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.log.convert.SysLoginLogConvert;
import com.xht.cloud.admin.module.log.dao.SysLoginLogDao;
import com.xht.cloud.admin.module.log.domain.dataobject.SysLoginLogDO;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.admin.module.user.factory.UserInfoFactory;
import com.xht.cloud.admin.module.user.factory.UserRouterFactory;
import com.xht.cloud.admin.module.user.service.IUserCenterService;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.treenode.INode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xht.cloud.admin.api.log.enums.LoginStatusEnums.LOGIN_OUT;

/**
 * 描述 ：用户中心
 *
 * @author : 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCenterServiceImpl implements IUserCenterService {

    private final SysLoginLogDao sysLoginLogDao;

    private final SysLoginLogConvert sysLoginLogConvert;

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
     * 获取登录日志
     *
     * @param maxSize 最大记录数 超过10取10 小于5取5
     * @return 登录日志
     */
    @Override
    public List<SysLoginLogResponse> getUserLoginLog(int maxSize) {
        LambdaQueryWrapper<SysLoginLogDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseThrow(() -> new UserException("获取不到登录信息"));
        lambdaQueryWrapper
                .eq(SysLoginLogDO::getUserId, userDetailsBO.getId())
                .eq(SysLoginLogDO::getUserType, userDetailsBO.getUserType())
                .ne(SysLoginLogDO::getLoginStatus, LOGIN_OUT)
                .orderByDesc(SysLoginLogDO::getLoginTime)
        ;
        if (maxSize < 5) maxSize = 5;
        if (maxSize > 10) maxSize = 10;
        IPage<SysLoginLogDO> page = sysLoginLogDao.page(PageTool.getPage(0, maxSize), lambdaQueryWrapper);
        return sysLoginLogConvert.convert(page.getRecords());
    }

}
