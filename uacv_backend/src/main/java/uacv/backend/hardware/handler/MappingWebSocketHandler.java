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

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingWebSocketHandler extends TextWebSocketHandler {

    // ROS 기기에서 수신할 메시지의 헤더 크기
    private static final int HEADER_SIZE = 192;

    // /socket/mapping에 접속한 클라이언트 정보
    private Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());
    // 맵핑 데이터 송신자 식별용 해시맵
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

        // ROS 기기에서 보낸 메시지인지 확인
        if (payload.length() >= 15 && payload.contains("header_data")) {
            log.trace("Incomming message has data header!");
            handleMappingData(session, message, payload);
        } else {
            log.debug("Incomming message does not have mapping data header: {}",
                    objectMapper.readValue(message.getPayload(), new TypeReference<Map<String, Object>>() {}));
        }
    }

    public void handleMappingData(WebSocketSession session, TextMessage message, String payload) throws Exception {

        // scan 주제로 들어오는 값 중 "Infinity"를 처리하기 위한 기능 설정
        objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());

        // 192 Byte 크기의 헤더 추출 후 빈 공간 제거
        String strippedHeader = payload.substring(0, HEADER_SIZE).trim();

        // 헤더 데이터 추출
        Map<String, Object> header = objectMapper.readValue(strippedHeader, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> headerData = objectMapper.convertValue(header.get("header_data"),
                new TypeReference<Map<String, Object>>() {});

        String chunk = payload.substring(HEADER_SIZE);

        String messageId = (String) headerData.get("message_id");
        String dataType = (String) headerData.get("data_type");
        int chunkNumber = (int) headerData.get("chunk_number");
        int totalChunks = (int) headerData.get("total_chunks");

        // 맵핑 데이터 송신자의 세션 정보와 보낸 데이터 종류 저장
        dataSender.put(session, dataType);

        log.trace("Received chunk for message ID: {}, Chunk: {}/{}, Data Type: {}", messageId, chunkNumber, totalChunks,
                dataType);
        messageBuffer.computeIfAbsent(messageId, k -> new StringBuilder()).append(chunk);

        if (chunkNumber == totalChunks) {
            log.debug("All chunks received for message ID: {} from session {}. Assembling complete message.", messageId,
                    session.getId());

            // 완성된 메시지를 Object로 변환
            String completeJsonMessage = messageBuffer.remove(messageId).toString();
            Map<String, Object> deserializedMessage = objectMapper.readValue(completeJsonMessage,
                    new TypeReference<Map<String, Object>>() {});

            // 주제와 메시지 추출 후 ROS 메시지로 변환
            String topic = (String) deserializedMessage.get("topic");
            Map<String, Object> topicMessage = objectMapper.convertValue(deserializedMessage.get("message"),
                    new TypeReference<Map<String, Object>>() {});

            log.debug("Message {}: Topic: {}, KeySet: {}", messageId, topic, topicMessage.keySet());

            String rosMessage = rosMessageBuilder(topic, topicMessage);

            // 변환된 ROS 메시지를 데이터 수신자(프론트엔드 세션)에게만 전송
            for (WebSocketSession client : clients) {
                if (client.isOpen() && !client.getId().equals(session.getId()) && dataSender.get(client) == null) {
                    client.sendMessage(new TextMessage(rosMessage));
                    log.debug("Message sent to session {}", client.getId());
                } else if (dataSender.get(client) != null) {
                    log.debug("Session {} is not receiver (Data type: {}), not sending {} data", client.getId(),
                            dataSender.get(client), dataType);
                }
            }
        }
    }

    public String rosMessageBuilder(String topic, Map<String, Object> topicMessage) throws Exception {
        log.trace("Building ROS message: Topic: {}, Body: {}", topic, topicMessage);

        // 입력된 메시지의 Key, Value 추출 후 메시지 본문 Object 생성
        ObjectNode messageBody = objectMapper.createObjectNode();
        for (String key : topicMessage.keySet()) {
            JsonNode msg = objectMapper.convertValue(topicMessage.get(key), JsonNode.class);
            messageBody.set(key, msg);
        }

        // Topic 발행용 ROS 메시지 생성
        ObjectNode rosMessage = objectMapper.createObjectNode();
        rosMessage.put("op", "publish")
                .put("topic", topic)
                .set("msg", messageBody);

        return objectMapper.writeValueAsString(rosMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("Closed websocket connection: {}", session);
        clients.remove(session);
        dataSender.remove(session);
    }
}
