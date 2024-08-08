package uacv.backend.hardware.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class RelayWebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession connectedSession) throws Exception {
        // System.out.println("Session Connected: " + connectedSession);
        sessions.add(connectedSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession connectedSession, TextMessage message) throws Exception {

        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        for (WebSocketSession session : sessions) {
            // 세션이 연결되어있고, 세션 ID가 자기 자신이 아닐 경우에 메시지 전송
            if (session.isOpen() && !session.getId().equals(connectedSession.getId())) {
                session.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession connectedSession, CloseStatus status) throws Exception {
        // System.out.println("Session Connected: " + connectedSession);
        sessions.remove(connectedSession);
    }

}
