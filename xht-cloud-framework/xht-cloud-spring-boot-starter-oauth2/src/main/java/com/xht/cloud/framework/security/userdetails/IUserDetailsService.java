package com.xht.cloud.framework.security.userdetails;

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
public interface IUserDetailsService extends UserDetailsService {


    /**
     * 查询用户
     *
     * @param userBuilder {@link RequestUserBO} 用户提交的用户信息
     * @return {@link UserDetailsBO} 查询出来的用户信息
     */
    default UserDetailsBO loadUser(RequestUserBO userBuilder) throws UsernameNotFoundException {
        return loadUserByUsername(userBuilder.getUsername());
    }

    @Override
    UserDetailsBO loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 是否支持此客户端校验
     *
     * @param grantType 授权模式
     * @param userType  用户类型
     * @return true/false
     */
    boolean support(String grantType, UserTypeEnums userType);

}
