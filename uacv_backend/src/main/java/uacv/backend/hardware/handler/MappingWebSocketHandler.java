package uacv.backend.hardware.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingWebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());
    private StringBuilder messageBuffer = new StringBuilder();

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Incomming: {}", session);

        if (!clients.contains(session)) {
            clients.add(session);
            log.debug("Connected: {}", session);
        } else {
            log.debug("Session {} is already connected", session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.debug("Receiving message chunk from session {} (Size: {})", session.getId(), message.getPayloadLength());
        String payload = message.getPayload();

        if (payload.endsWith("__LAST_CHUNK__")) {
            payload = payload.replace("__LAST_CHUNK__", "");
            messageBuffer.append(payload);
            log.debug("Received Last Chunk! (Total: {})", messageBuffer.length());

            String receivedMessage = messageBuffer.toString();

            for (WebSocketSession ws : clients) {
                // 세션이 연결되어있고, 세션 ID가 자기 자신이 아닐 경우에 메시지 전송
                if (ws.isOpen() && !ws.getId().equals(session.getId())) {
                    ws.sendMessage(new TextMessage(receivedMessage));
                    log.debug("Message sent to session {}", ws.getId());
                }
            }
            messageBuffer.setLength(0);
        } else {
            messageBuffer.append(payload);
            log.debug("Partial Message: {} (Total: {})", message, messageBuffer.length());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("Closed: {}", session);
        clients.remove(session);
    }
}
