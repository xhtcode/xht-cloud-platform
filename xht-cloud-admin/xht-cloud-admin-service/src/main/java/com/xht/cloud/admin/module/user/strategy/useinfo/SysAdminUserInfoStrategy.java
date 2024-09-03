package com.xht.cloud.admin.module.user.strategy.useinfo;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.dao.SysUserAdminDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserAdminDO;
import com.xht.cloud.admin.module.user.strategy.AbstractUserInfoStrategy;
import com.xht.cloud.framework.exception.user.UserNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 描述 ：系统管理员用户策略
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SysAdminUserInfoStrategy extends AbstractUserInfoStrategy<SysUserAdminDO> {

    private final SysUserAdminDao sysUserAdminDao;

    /**
     * 根据用户账号获取数据库用户数据
     *
     * @param userName 用户账号
     * @return 数据库用户数据
     */
    @Override
    protected SysUserAdminDO userName(String userName) {
        return sysUserAdminDao.selectOne(SysUserAdminDO::getUserName, userName).orElseThrow(UserNotFountException::new);
    }

    /**
     * 根据用户id获取数据库用户数据
     *
     * @param userId 用户id
     * @return 数据库用户数据
     */
    @Override
    protected SysUserAdminDO userId(String userId) {
        return sysUserAdminDao.selectOne(SysUserAdminDO::getId, userId).orElseThrow(UserNotFountException::new);
    }

    /**
     * 用户构建
     *
     * @param userInfo 已从数据库查询出的用户信息
     * @return 用户信息
     */
    @Override
    protected UserCenterResponse builder(SysUserAdminDO userInfo) {
        UserCenterResponse response = new UserCenterResponse();
        Integer userId = userInfo.getId();
        response.setUserId(String.valueOf(userId));
        response.setUserType(getUserType());
        response.setUserName(userInfo.getUserName());
        response.setNickName(userInfo.getUserName());
        response.setPassWord(userInfo.getPassWord());
        response.setPassWordSalt(null);
        response.setDeptId(null);
        response.setContactMobile(userInfo.getContactPhone());
        response.setDataScope(DeptUserDataScopeEnum.DATA_SCOPE_ALL);
        response.setUserAvatar(userInfo.getUserAvatar());
        response.setUserStatus(UserStatusEnums.NORMAL);
        response.setRegisteredTime(userInfo.getCreateTime());
        response.setRoleCode(Collections.emptySet());
        response.setAuthorityCode(Collections.emptySet());
        response.setDataSource("暂无设置");
        return response;
    }


    /**
     * 获取用户类型
     */
    @Override
    public UserTypeEnums getUserType() {
        return UserTypeEnums.ADMIN;
    }
}
