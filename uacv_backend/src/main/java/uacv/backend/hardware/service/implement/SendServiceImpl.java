package uacv.backend.hardware.service.implement;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import uacv.backend.hardware.config.RabbitmqProperties;
import uacv.backend.hardware.domain.EventType;
import uacv.backend.hardware.domain.LogType;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.service.SendService;

// 송신 서비스 구현체
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final RabbitTemplate rabbitTemplate;
    private RabbitmqProperties rabbitmqProperties;

    @Override
    public void sendCommand(String routingKey, ControlDataDto controlDataDto) {
        // try {
        //     // 객체 -> json
        //     ObjectMapper objectMapper = new ObjectMapper();
        //     String objectToJson = objectMapper.writeValueAsString(controlDataDto);
        //     rabbitTemplate.convertAndSend("test-exchange", "test-key", objectToJson);
        // } catch (JsonProcessingException e) {
        //     System.err.println("Failed to parse json");
        // }
        // RabbitmqProperties.QueueConfig targetQueue = rabbitmqProperties.getQueues().get(queueName);
        // if (targetQueue == null) {
        //     throw new IllegalArgumentException("No configuration found for queue: " + targetQueue);
        // }

        rabbitTemplate.convertAndSend(routingKey, controlDataDto);
    }

    @Override
    public void getDeviceLogs(LogType logType, EventType eventType, int pageCount) {

    }
}
