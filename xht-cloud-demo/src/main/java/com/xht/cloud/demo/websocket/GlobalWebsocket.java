package com.xht.cloud.demo.websocket;

import com.xht.cloud.demo.pojo.MessageVo;
import com.xht.cloud.framework.utils.jackson.JsonUtils;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@ServerEndpoint("/globalWs/{token}")
@Component
public class GlobalWebsocket extends SecureWebSocket {


    /**
     * key: userKye
     * value: GlobalWebsocket  这里你直接存储 session 也是可以的
     */
    private static final Map<String, GlobalWebsocket> CLIENTS = new ConcurrentHashMap<>();

    /**
     * // 如果允许 一个账号 多人登录的话  就 加上  "-" + tokenTime，因为每次登录的token过期时间都是不一样的
     * clientUserInfo.getId() + "-" + clientUserInfo.getAccount() ;
     */
    private String userKye;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        if (!isTokenValid(token, true)) {
            sendAuthFailed(session);
            return;
        }

        this.session = session;
        this.userKye = "123123";
        CLIENTS.put(userKye, this);
        log.info("当前在线用户:{}", CLIENTS.keySet());

        try {
            session.getBasicRemote().sendText("连接成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText("received");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
//        log.error("ws session 发生错误,session key is {}",throwable);
        log.error("ws session 发生错误:{}", throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session) {
        CLIENTS.remove(userKye);
        log.info("ws 用户 userKey {} 已下线,当前在线用户:{}", userKye, CLIENTS.keySet());
    }

    /**
     * 发送消息
     *
     * @param messageVo
     */
    public void sendMessage(MessageVo messageVo) {
        try {
            this.session.getBasicRemote().sendText(JsonUtils.toJsonString(messageVo));
        } catch (IOException e) {
            log.error("发送消息异常", e);
        }
    }

    /**
     * 向user精确用户发送消息
     *
     * @param userKey   由 account + "-" + refreshToken的签发时间组成，例："admin-1635830649000"
     * @param messageVo 消息内容
     */
    public static void sendToUser(String userKey, MessageVo messageVo) {

        GlobalWebsocket globalWebsocket = CLIENTS.get(userKey);
        if (null != globalWebsocket) {
            globalWebsocket.sendMessage(messageVo);
            return;
        }
        log.error("发送消息到指定用户,但是用户不存在,userKey is {},message is {}", userKey, JsonUtils.toJsonString(messageVo));
    }

    /**
     * 全体组播消息
     *
     * @param
     */
    public static void broadcast(MessageVo messageVo) {
        CLIENTS.values().forEach(c -> {
                    Session curSession = c.session;
                    if (curSession.isOpen()) {
                        try {
                            curSession.getBasicRemote().sendText(JsonUtils.toJsonString(messageVo));
                        } catch (IOException e) {
                            log.error("发送ws数据错误:{}", e.getMessage());
                        }
                    }
                }
        );
    }
}