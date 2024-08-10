package uacv.backend.hardware.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SoundMessageListener {
    private final SimpMessagingTemplate messagingTemplate;

    public SoundMessageListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "sensor_queue")
    public void receiveMessage(String message) {
        System.out.println("Received sound message: " + message);
        messagingTemplate.convertAndSend("/orin/sensor", message);
    }
}
