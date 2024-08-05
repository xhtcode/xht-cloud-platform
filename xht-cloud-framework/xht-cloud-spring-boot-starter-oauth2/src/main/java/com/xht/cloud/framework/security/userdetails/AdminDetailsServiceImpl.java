package com.xht.cloud.framework.security.userdetails;

import com.xht.cloud.admin.api.user.dto.SysAdminUserResDTO;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.api.user.feign.AdminUserClient;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.ROptional;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.xht.cloud.framework.security.constant.CustomAuthorizationGrantType.PASSWORD;

/**
 * 描述 ：用于检索用户进行身份验证
 *
 * @author 小糊涂
 **/
@Slf4j
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements IUserDetailsService {

    @Resource
    private AdminUserClient adminUserClient;

    /**
     * 用于检索用户进行身份验证
     *
     * @param username 用户名
     */
    @Override
    public UserDetailsBO loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用于检索用户进行身份验证 {}", username);
        R<SysAdminUserResDTO> userByAccount = adminUserClient.findUserByAccount(username);
        ROptional<SysAdminUserResDTO> rOptional = ROptional.of(userByAccount);
        String errorMessage = String.format("`%s`账号不存在", username);
        if (rOptional.isSuccess()) {
            SysAdminUserResDTO userResDTO = rOptional.getData().orElseThrow(() -> new UsernameNotFoundException(errorMessage));
            return getUserDetailsBO(userResDTO);
        }
        throw new UsernameNotFoundException(errorMessage);
    }

    private static UserDetailsBO getUserDetailsBO(SysAdminUserResDTO userResDTO) {
        UserDetailsBO userDetailsBO = new UserDetailsBO();
        userDetailsBO.setUserAccount(userResDTO.getUserName());
        userDetailsBO.setUsername(userResDTO.getUserName());
        userDetailsBO.setPassword(userResDTO.getPassWord());
        userDetailsBO.setSuperAdmin(SuperAdminUserEnums.YES);
        userDetailsBO.setDataScope(null);
        userDetailsBO.setUserStatus(UserStatusEnums.NORMAL);
        userDetailsBO.setMobile(userResDTO.getContactPhone());
        userDetailsBO.setDeptId(null);
        userDetailsBO.setMenuCode(null);
        userDetailsBO.setRoleCode(null);
        userDetailsBO.setSourceName("system");
        userDetailsBO.setId(userResDTO.getId() + "");
        return userDetailsBO;
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
