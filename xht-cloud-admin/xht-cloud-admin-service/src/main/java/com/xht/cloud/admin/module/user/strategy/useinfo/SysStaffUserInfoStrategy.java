package com.xht.cloud.admin.module.user.strategy.useinfo;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.permissions.dao.SysMenuDao;
import com.xht.cloud.admin.module.permissions.dao.SysRoleDao;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.strategy.AbstractUserInfoStrategy;
import com.xht.cloud.framework.exception.user.UserNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 描述 ：系统用户策略
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SysStaffUserInfoStrategy extends AbstractUserInfoStrategy<SysUserStaffDO> {

    private final SysUserStaffDao sysUserStaffDao;

    private final SysRoleDao sysRoleDao;

    private final SysMenuDao sysMenuDao;

    /**
     * 根据用户账号获取数据库用户数据
     *
     * @param userName 用户账号
     * @return 数据库用户数据
     */
    @Override
    protected SysUserStaffDO userName(String userName) {
        return sysUserStaffDao.selectOne(SysUserStaffDO::getUserName, userName).orElseThrow(UserNotFountException::new);
    }

    /**
     * 根据用户id获取数据库用户数据
     *
     * @param userId 用户id
     * @return 数据库用户数据
     */
    @Override
    protected SysUserStaffDO userId(String userId) {
        return sysUserStaffDao.selectOne(SysUserStaffDO::getId, userId).orElseThrow(UserNotFountException::new);
    }

    /**
     * 用户构建
     *
     * @param userInfo 已从数据库查询出的用户信息
     * @return 用户信息
     */
    @Override
    protected UserCenterResponse builder(SysUserStaffDO userInfo) {
        UserCenterResponse response = new UserCenterResponse();
        String userId = userInfo.getId();
        Set<String> roleCode = sysRoleDao.getRoleCode(userId);
        Set<String> authorityCode = sysMenuDao.selectMenuAuthority(userId);
        response.setUserId(userId);
        response.setUserType(UserTypeEnums.STAFF);
        response.setUserName(userInfo.getUserName());
        response.setNickName(userInfo.getNickName());
        response.setPassWord(userInfo.getPassWord());
        response.setPassWordSalt(userInfo.getPassWordSalt());
        response.setDeptId(userInfo.getDeptId());
        response.setDataScope(userInfo.getDataScope());
        response.setUserAvatar(userInfo.getUserAvatar());
        response.setUserStatus(userInfo.getUserStatus());
        response.setRegisteredTime(userInfo.getRegisteredTime());
        response.setRoleCode(roleCode);
        response.setAuthorityCode(authorityCode);
        response.setDataSource("暂无设置");
        return response;
    }

    /**
     * 获取用户类型
     */
    @Override
    public UserTypeEnums getUserType() {
        return UserTypeEnums.STAFF;
    }
}
