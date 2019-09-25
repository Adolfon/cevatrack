package com.ceva.cevatrack.websocket;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * WebsocketConfig class.
 * 
 * @author varsha.anumolu
 *
 */
public class WebsocketConfig extends Configurator {

    /**
     * This class is overriden to access the currently logged in userPrincipal.
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put("UserPrincipal", request.getUserPrincipal());
    }
}
