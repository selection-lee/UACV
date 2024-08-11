package uacv.backend.hardware.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingWebSocketHandler extends TextWebSocketHandler {

    private static final int HEADER_SIZE = 192;

    private Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());
    private Map<WebSocketSession, String> dataSender = new ConcurrentHashMap<>();
    private Map<String, StringBuilder> messageBuffer = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Incomming websocket connection: {}", session);

        if (!clients.contains(session)) {
            clients.add(session);
            log.debug("Connected: {}", session);
        } else {
            log.debug("Session {} is already connected", session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        String headerJson = payload.substring(0, HEADER_SIZE).trim();
        Map<String, Object> header = objectMapper.readValue(headerJson, new TypeReference<Map<String, Object>>() {
        });

        String chunk = payload.substring(HEADER_SIZE);

        String messageId = (String) header.get("message_id");
        String dataType = (String) header.get("data_type");
        int chunkNumber = (int) header.get("chunk_number");
        int totalChunks = (int) header.get("total_chunks");

        dataSender.put(session, dataType);

        log.trace("Received chunk for message ID: {}, Chunk: {}/{}, Data Type: {}", messageId, chunkNumber, totalChunks,
                dataType);
        messageBuffer.computeIfAbsent(messageId, k -> new StringBuilder()).append(chunk);

        if (chunkNumber == totalChunks) {
            log.debug("All chunks received for message ID: {} from session {}. Assembling complete message.", messageId,
                    session.getId());
            String completeMessage = messageBuffer.remove(messageId).toString();

            for (WebSocketSession client : clients) {
                if (client.isOpen() && !client.getId().equals(session.getId()) && dataSender.get(client) == null) {
                    log.debug("Message sent to session {}", client.getId());
                    client.sendMessage(new TextMessage(completeMessage));
                } else if (dataSender.get(client) != null) {
                    log.debug("Session {} is not receiver (Data type: {}), not sending {} data", client.getId(),
                            dataSender.get(client), dataType);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("Closed websocket connection: {}", session);
        clients.remove(session);
    }
}
