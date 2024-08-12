package uacv.backend.sound.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uacv.backend.sound.dto.SoundDataDTO;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private ObjectMapper objectMapper;

    @PostMapping("/send")
    public String sendTestMessage(@RequestBody SoundDataDTO soundDataDTO) {
        try {
            rabbitTemplate.convertAndSend("amq.topic", "orin.sensor", soundDataDTO);
            return "Message sent to RabbitMQ: " + soundDataDTO.getType();
        } catch (Exception e) {
            return "Error sending message: " + e.getMessage();
        }
    }
//    public String sendTestMessage(@RequestBody SoundDataDTO soundDataDTO) {
//        try {
//            String message = objectMapper.writeValueAsString(soundDataDTO);
//            rabbitTemplate.convertAndSend("amq.topic", "orin.sensor", message);
//            return "Message sent to RabbitMQ: " + message;
//        } catch (Exception e) {
//            return "Error sending message: " + e.getMessage();
//        }
//    }
//    public String sendTestMessage(@RequestBody String message) {
//        rabbitTemplate.convertAndSend("amq.topic", "orin.sensor", message);
//        return "Message sent to RabbitMQ";
//    }
}
