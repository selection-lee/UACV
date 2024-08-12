package uacv.backend.sound.api;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public String sendTestMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend("amq.topic", "orin.sensor", message);
        return "Message sent to RabbitMQ";
    }
}
