package com.xht.cloud.framework.security.convert;

import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.framework.security.domain.UserDetailsBO;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@FunctionalInterface
public interface SysUserConvert {

    UserDetailsBO convert(SysUserResDTO sysUserResDTO);

}
