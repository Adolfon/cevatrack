package com.ceva.cevatrack.filenavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

public class FileTailManager {

    private static Map<String, Tailer> tailers = new HashMap<>();
    private static Map<String, TailerListener> tailListeners = new HashMap<>();
    private static Map<TailerListener, List<Session>> subscribedSessions = new HashMap<>();

    private static void createTailer(String filePath, Session session) {
        TailerListener listener = new FileTailListener(filePath);
        tailListeners.put(filePath, listener);
        Tailer tailer = new Tailer(new File(filePath), listener, 500, true);
        tailers.put(filePath, tailer);
        List<Session> sessions = new ArrayList<Session>();
        sessions.add(session);
        subscribedSessions.put(listener, sessions);
        tailer.run();
    }

    /**
     * Creates new Tailer for a file if none exists.
     */
    public static void subscribeTailFollow(String filePath, Session session) {
        Tailer tailer = tailers.get(filePath);
        if (tailer == null) {
            createTailer(filePath, session);
        }

        TailerListener tailerListener = tailListeners.get(filePath);
        List<Session> subscribers = subscribedSessions.get(tailerListener);
        subscribers.add(session);
    }

    /**
     * Removes the Tailer object for the given file when the websocket session is broken.
     */
    public static void unSubscribeTailFollow(String filePath, List<Session> brokenSessions) {
        brokenSessions.stream().forEach(session -> unSubscribeTailFollow(filePath, session));
    }

    /**
     * Removes the Tailer object for the given file when the websocket session is closed.
     */
    public static void unSubscribeTailFollow(String filePath, Session session) {
        TailerListener tailerListener = tailListeners.get(filePath);
        List<Session> subscribers = subscribedSessions.get(tailerListener);
        subscribers.remove(session);
        if (subscribers.isEmpty()) {
            tailers.remove(filePath);
            tailListeners.remove(filePath);
        }
    }

    /**
     * Collects the sessions subscribed for a filePath.
     */
    public static List<Session> getSubscribedSessions(String filePath) {
        TailerListener tailerListener = tailListeners.get(filePath);
        return subscribedSessions.get(tailerListener);
    }
}
