package com.ceva.cevatrack.filenavigator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTailListener extends TailerListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTailListener.class);

    private String filePath;

    public FileTailListener(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Overriding Handle method to print the file content through websocket.
     */
    @Override
    public void handle(String line) {
        final List<Session> brokenSessions = new ArrayList<>();

        List<Session> subscribedSessions = FileTailManager.getSubscribedSessions(filePath);
        subscribedSessions.stream().forEach(session -> {
            try {
                synchronized (session) {
                    session.getBasicRemote().sendText(line);
                }
            } catch (IOException e) {
                brokenSessions.add(session);
                e.printStackTrace();
            }
        });
        FileTailManager.unSubscribeTailFollow(filePath, brokenSessions);
    }
}
