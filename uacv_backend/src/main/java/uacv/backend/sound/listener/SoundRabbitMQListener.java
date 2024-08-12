package uacv.backend.sound.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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

@Slf4j
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
//    @RabbitListener(queues = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.name')}",bindings = )
//    @RabbitListener(bindings = @QueueBinding(
////            exchange = @Exchange(name = "amq.topic",type = ExchangeTypes.TOPIC),
//            value = @Queue(name = "sensor_queue"),
//            key = "orin.sensor"
//    ))
//    @RabbitListener(
//            queues = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.name')}",
//            bindings = @QueueBinding(
//                    value = @Queue(value = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.name')}", durable = "true"),
//                    exchange = @Exchange(value = "#{@environment.getProperty('spring.rabbitmq.exchange')}", type = ExchangeTypes.TOPIC),
//                    key = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.routing-keys')}"
//            )
//    )
//    @RabbitListener(
//            bindings = @QueueBinding(
//                    value = @Queue(value = "#{@environment.getProperty('spring.rabbitmq.queues.sensor.name')}", durable = "true"),
//                    exchange = @Exchange(value = "amq.topic", type = ExchangeTypes.TOPIC),
//                    key = "orin.sensor"
//            )
//    )
    @RabbitListener(queues = "sound_queue")
    @Transactional
    public void receiveSoundData(Message message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            // 메시지 본문을 바이트 배열로 가져옴
            byte[] body = message.getBody();
            // 바이트 배열을 String으로 변환
            String messageContent = new String(body);

            // EDIT: Improved logging
            log.debug("Received message: " , messageContent);
            log.debug("Received routing key: " , routingKey);

            // EDIT: Check for specific routing key
            if ("orin.sensor".equals(routingKey)) {
                SoundDataDTO soundDataDTO = objectMapper.readValue(messageContent, SoundDataDTO.class);
                SoundData soundData = new SoundData(soundDataDTO.getType());
                
                // 데이터 저장
                soundDataRepository.save(soundData);
                SoundData savedData = soundDataService.saveSoundData(soundData);

                // EDIT: Construct a more informative message
                String clientMessage = String.format("Sound detected: %s", soundDataDTO.getType());
                messagingTemplate.convertAndSend("/orin/sensor", "{\"message\": \"" + clientMessage + "\"}");
            }

        } catch (Exception e) {
            // EDIT: Improved error logging
            // 에러 로깅 개선
            log.error("Error processing sound data: ", e);
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
