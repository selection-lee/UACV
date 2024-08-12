package uacv.backend.sound.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.dto.SoundDataDTO;
import uacv.backend.sound.repository.SoundDataRepository;
import uacv.backend.sound.service.SoundDataService;

@Component
public class SoundRabbitMQListener {

    @Autowired
    private SoundDataRepository soundDataRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SoundDataService soundDataService;

    // EDIT: Use SpEL to reference the queue name from application.yml
    @RabbitListener(queues = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.name')}")
    @Transactional
    public void receiveSoundData(String message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        System.out.println(11111111);
        try {
            // EDIT: Improved logging
            System.out.println("Received message: " + message);
            System.out.println("Received routing key: " + routingKey);
//            logger.debug("Received message: {} with routing key: {}", message, routingKey);
            // EDIT: Check for specific routing key
            if ("orin.sensor".equals(routingKey)) {
                SoundDataDTO soundDataDTO = objectMapper.readValue(message, SoundDataDTO.class);
                SoundData soundData = new SoundData(soundDataDTO.getType());
                soundDataRepository.save(soundData);
                SoundData savedData = soundDataService.saveSoundData(soundData);

                // EDIT: Construct a more informative message
                String clientMessage = String.format("Sound detected: %s", soundDataDTO.getType());
                messagingTemplate.convertAndSend("/orin/sensor", "{\"message\": \"" + clientMessage + "\"}");
            }

        } catch (Exception e) {
            // EDIT: Improved error logging
            System.err.println("Error processing sound data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


//package uacv.backend.hardware.listener;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
////import org.springframework.amqp.core.AmqpHeaders;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//import uacv.backend.hardware.domain.SoundData;
//import uacv.backend.hardware.dto.SoundDataDTO;
//import uacv.backend.hardware.repository.SoundDataRepository;
//
//@Component
//public class SoundRabbitMQListener {
////    @Autowired
////    private Environment env;
//    @Value("${rabbitmq.queues.sensor.name:sensor_queue}")
//    private String soundQueueName;
//
//
//    @Autowired
//    private SoundDataRepository soundDataRepository;
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
////    @RabbitListener(queues = "#{@environment.getProperty('rabbitmq.queues.sensor.name')}")
//    @RabbitListener(queues = "#{@environment.getProperty('rabbitmq.queues.sensor.name', 'sensor_queue')}")
//    public void receiveSoundData(String message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
////    public void receiveSoundData(String message) {
//        try {
//            // 메시지와 라우팅 키를 로그로 출력하여 디버깅
//            System.out.println(11111111);
//            System.out.println("Received message: " + message);
//            System.out.println("Received routing key: " + routingKey);
//
//            if ("orin.sensor".equals(routingKey)) {
//                SoundDataDTO soundDataDTO = objectMapper.readValue(message, SoundDataDTO.class);
//                SoundData soundData = new SoundData(soundDataDTO.getType());
//                soundDataRepository.save(soundData);
//
//                String clientMessage = String.format("Sound detected: %s", soundDataDTO.getType());
//                messagingTemplate.convertAndSend("/orin/sensor", "{\"message\": \"" + clientMessage + "\"}");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
