package com.dev.utils;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessagesHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();
    private static int totalSessions;
    private static Map<String, WebSocketSession> sessionMap = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Map<String, String> map = Utils.splitQuery(session.getUri().getQuery());
        sessionMap.put(map.get("token"), session);
        sessionList.add(session);
        totalSessions = sessionList.size();
        System.out.println("afterConnectionEstablished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("handleTextMessage");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("afterConnectionClosed");
    }

    public void sendNotification (JSONObject jsonObject, List<String> usersTokens) {
        for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
            if (usersTokens.contains(entry.getKey())) {
                try {
                    System.out.println("SENT HERE");
                    if (entry.getValue().isOpen()) {
                        entry.getValue().sendMessage(new TextMessage(jsonObject.toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendNotificationToAll(JSONObject jsonObject) {
        for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
            try {
                System.out.println("SENT GLOBALLY");
                if (entry.getValue().isOpen()) {
                    entry.getValue().sendMessage(new TextMessage(jsonObject.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}