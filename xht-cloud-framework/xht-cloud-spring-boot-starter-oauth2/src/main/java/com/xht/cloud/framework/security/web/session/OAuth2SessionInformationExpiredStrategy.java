package com.xht.cloud.framework.security.web.session;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * 描述 ：后登录的账号会使先登录的账号失效
 *
 * @author 小糊涂
 **/
@Slf4j
public class OAuth2SessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        SessionInformation sessionInformation = event.getSessionInformation();
        log.info("{} 该账号已从其他设备登录", sessionInformation.getPrincipal());
        HttpServletUtils.writeString(response, R.failed().appendMsg("该账号已从其他设备登录").setData(sessionInformation.getPrincipal()));
    }
}
