package com.xht.cloud.demo.websocket;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
public class SecureWebSocket {

    protected Session session;

    /**
     * 验证token是否有效（包含有效期）
     *
     * @param token  token
     * @param isInit 是否对token和userInfo进行初始化赋值
     * @return boolean
     */
    protected boolean isTokenValid(String token, boolean isInit) {
        System.out.println("token: " + token);
        System.out.println("isInit: " + isInit);
        return true;
    }

    /**
     * 认证失败，断开连接
     *
     * @param session session
     */
    protected void sendAuthFailed(Session session) {
        try {
            session.getBasicRemote().sendText("认证失败");
            session.close();
        } catch (IOException e) {
            log.error("认证失败 {}", e.getMessage(), e);
        }
    }
}
