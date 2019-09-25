package com.ceva.cevatrack.websocket;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class acts as websocket endpoint.
 * 
 * @author varsha.anumolu
 *
 */
@ServerEndpoint(value = "/todo", configurator = WebsocketConfig.class)
public class WebsocketEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEndPoint.class);

    public WebsocketEndPoint() {
        LOGGER.info("class loaded " + this.getClass());
    }

    private static Map<String, Session> sessionMap = new HashMap<String, Session>();

    Principal userPrincipal;

    /**
     * This method invokes the websocket connection.
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        userPrincipal = (Principal) config.getUserProperties().get("UserPrincipal");
        sessionMap.put(userPrincipal.getName(), session);
        LOGGER.info("Session opened, id: {}", session.getId());
    }

    /**
     * This method is invoked when a message is sent to the user session.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String todoMessage = message.substring(0, message.indexOf("owner"));
        String owner = message.substring(message.lastIndexOf(":") + 1);
        try {
            for (String user : sessionMap.keySet()) {
                Session ownerSession = sessionMap.get(user);
                if (user.equals(owner) && ownerSession != null) {
                    ownerSession.getBasicRemote().sendText(todoMessage);
                }
            }
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * This method is invoked when an error is thrown.
     */
    @OnError
    public void onError(Throwable e) {
        LOGGER.error(e.getMessage());
    }

    /**
     * This method is invoked when the websocket session is closed.
     */
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("Session closed with id: {}", session.getId());
    }

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }

    public static void setSessionMap(Map<String, Session> sessionMap) {
        WebsocketEndPoint.sessionMap = sessionMap;
    }

    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }
}
