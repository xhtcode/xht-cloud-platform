package com.xht.cloud.framework.security.userdetails;

import com.xht.cloud.admin.api.user.dto.UserCenterResponse;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.api.user.feign.UserCenterClient;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.utils.ROptional;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.xht.cloud.framework.security.constant.CustomAuthorizationGrantType.PASSWORD;

/**
 * 描述 ：用于检索用户进行身份验证
 *
 * @author 小糊涂
 **/
@Slf4j
public class AdminDetailsServiceImpl extends IUserDetailsService {

    @Autowired
    private UserCenterClient userCenterClient;

    /**
     * 用于检索用户进行身份验证
     *
     * @param username 用户名
     */
    @Override
    public UserDetailsBO loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用于检索用户进行身份验证 {}", username);
        R<UserCenterResponse> userByUserName = userCenterClient.findUserByUserName(username, UserTypeEnums.ADMIN);
        ROptional<UserCenterResponse> responseROptional = ROptional.of(userByUserName);
        String errorMessage = String.format("`%s`账号不存在", username);
        if (responseROptional.isSuccess()) {
            UserCenterResponse userResDTO = responseROptional.getData().orElseThrow(() -> new UsernameNotFoundException(errorMessage));
            UserDetailsBO userDetailsBO = getUserDetailsBO(userResDTO);
            userDetailsBO.setSuperAdmin(SuperAdminUserEnums.YES);
            return userDetailsBO;
        }
        throw new UsernameNotFoundException(errorMessage);
    }


    /**
     * 是否支持此客户端校验
     *
     * @param grantType 授权模式
     * @param userType  用户类型
     * @return true/false
     */
    @Override
    public boolean support(String grantType, UserTypeEnums userType) {
        return PASSWORD.getValue().equals(grantType) && UserTypeEnums.ADMIN.equals(userType);
    }

}
