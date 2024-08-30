package com.xht.cloud.framework.security.userdetails;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.security.domain.RequestUserBO;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 描述 ：自定义用户封装
 *
 * @author 小糊涂
 **/
public abstract class IUserDetailsService implements UserDetailsService {


    /**
     * 查询用户
     *
     * @param userBuilder {@link RequestUserBO} 用户提交的用户信息
     * @return {@link UserDetailsBO} 查询出来的用户信息
     */
    public final UserDetailsBO loadUser(RequestUserBO userBuilder) throws UsernameNotFoundException {
        return loadUserByUsername(userBuilder.getUsername());
    }

    @Override
    public abstract UserDetailsBO loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 是否支持此客户端校验
     *
     * @param grantType 授权模式
     * @param userType  用户类型
     * @return true/false
     */
    public abstract boolean support(String grantType, UserTypeEnums userType);


    protected final UserDetailsBO getUserDetailsBO(UserCenterResponse response) {
        UserDetailsBO userDetailsBO = new UserDetailsBO();
        userDetailsBO.setNickName(response.getUserName());
        userDetailsBO.setUsername(response.getUserName());
        userDetailsBO.setPassword(response.getPassWord());
        userDetailsBO.setUserType(response.getUserType());
        userDetailsBO.setSuperAdmin(SuperAdminUserEnums.NO);
        userDetailsBO.setDataScope(response.getDataScope());
        userDetailsBO.setUserStatus(response.getUserStatus());
        userDetailsBO.setMobile("暂未设置");
        userDetailsBO.setDeptId(response.getDeptId());
        userDetailsBO.setMenuCode(response.getAuthorityCode());
        userDetailsBO.setRoleCode(null);
        userDetailsBO.setSourceName("system");
        userDetailsBO.setId(response.getUserId());
        return userDetailsBO;
    }

}
