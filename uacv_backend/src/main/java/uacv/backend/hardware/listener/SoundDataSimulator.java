package uacv.backend.hardware.listener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SoundDataSimulator {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SoundDataSimulator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 데이터 전송
    public void sendMockSoundData() {
        String mockData = "{\"message\":\"Test sound data\"}";
        rabbitTemplate.convertAndSend("sensor_queue", mockData);
        System.out.println("Mock data sent to RabbitMQ: " + mockData);
    }
}
