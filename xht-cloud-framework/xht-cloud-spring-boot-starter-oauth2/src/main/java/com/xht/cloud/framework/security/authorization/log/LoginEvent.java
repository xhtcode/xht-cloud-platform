package com.xht.cloud.framework.security.authorization.log;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 描述 ：登录日志
 *
 * @author : 小糊涂
 **/
@Getter
public class LoginEvent extends ApplicationEvent {

    private final SysLoginLogDTO sysLoginLogDTO;

    public LoginEvent(SysLoginLogDTO sysLoginLogDTO) {
        super(sysLoginLogDTO);
        this.sysLoginLogDTO = sysLoginLogDTO;
    }
}
