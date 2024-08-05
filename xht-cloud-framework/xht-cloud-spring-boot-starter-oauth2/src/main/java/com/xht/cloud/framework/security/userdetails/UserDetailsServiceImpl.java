package com.xht.cloud.framework.security.userdetails;

import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.api.user.feign.UserInfoClient;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.ROptional;
import com.xht.cloud.framework.security.convert.SysUserConvert;
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
public class UserDetailsServiceImpl implements IUserDetailsService {

    @Resource
    private UserInfoClient userInfoClient;

    /**
     * 用于检索用户进行身份验证
     *
     * @param username 用户名
     */
    @Override
    public UserDetailsBO loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用于检索用户进行身份验证 {}", username);
        R<SysUserResDTO> userByAccount = userInfoClient.findUserByAccount(username);
        ROptional<SysUserResDTO> rOptional = ROptional.of(userByAccount);
        String errorMessage = String.format("`%s`账号不存在", username);
        if (rOptional.isSuccess()) {
            SysUserResDTO sysUserResDTO = rOptional.getData().orElseThrow(() -> new UsernameNotFoundException(errorMessage));
            return sysUserConvert.convert(sysUserResDTO);
        }
        throw new UsernameNotFoundException(errorMessage);
    }

    final SysUserConvert sysUserConvert = sysUserResDTO1 -> {
        UserDetailsBO userDetailsBO = new UserDetailsBO();
        userDetailsBO.setId(sysUserResDTO1.getUserId());
        userDetailsBO.setUserAccount(sysUserResDTO1.getUserAccount());
        userDetailsBO.setUsername(sysUserResDTO1.getUserName());
        userDetailsBO.setPassword(sysUserResDTO1.getPassWord());
        userDetailsBO.setSuperAdmin(sysUserResDTO1.getSuperAdmin());
        userDetailsBO.setDataScope(sysUserResDTO1.getDataScope());
        userDetailsBO.setUserStatus(sysUserResDTO1.getUserStatus());
        userDetailsBO.setMobile(sysUserResDTO1.getMobile());
        userDetailsBO.setDeptId(sysUserResDTO1.getDeptId());
        userDetailsBO.setMenuCode(sysUserResDTO1.getMenuCode());
        userDetailsBO.setRoleCode(sysUserResDTO1.getRoleCode());
        userDetailsBO.setSourceName(sysUserResDTO1.getSourceName());
        return userDetailsBO;
    };

    /**
     * 是否支持此客户端校验
     *
     * @param grantType 授权模式
     * @param userType  用户类型
     * @return true/false
     */
    @Override
    public boolean support(String grantType, UserTypeEnums userType) {
        return PASSWORD.getValue().equals(grantType) && UserTypeEnums.SYSTEM_USER.equals(userType);
    }
}
