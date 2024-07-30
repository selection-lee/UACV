package uacv.backend.hardware.service.implement;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.service.SendService;

// 송신 서비스 구현체
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {
    
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(ControlDataDto controlDataDto) {
        try {
            // 객체 -> json
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(controlDataDto);
            rabbitTemplate.convertAndSend("test-exchange", "test-key", objectToJson);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse json");
        }
    }
}
