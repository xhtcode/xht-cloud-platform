package com.xht.cloud.framework.security.authorization.log;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.api.log.feign.SysLoginLogClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.Objects;

/**
 * 描述 ：系统日志存储
 *
 * @author : 小糊涂
 **/
@Slf4j
public class LoginApplicationListener implements ApplicationListener<LoginEvent> {

    @Resource
    private SysLoginLogClient loginLogClient;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(LoginEvent event) {
        SysLoginLogDTO sysLoginLogDTO = event.getSysLoginLogDTO();
        if (Objects.nonNull(sysLoginLogDTO)) {
            loginLogClient.saveLoginLog(sysLoginLogDTO);
        }
    }
}
