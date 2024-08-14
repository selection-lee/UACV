package uacv.backend.sound.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.dto.SoundDataDTO;
import uacv.backend.sound.dto.SoundLogDTO;
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

    @Transactional
    @RabbitListener(queues = "sound_queue")
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
                messagingTemplate.convertAndSend("/orin/sensor", savedData);

                // 로그 페이지로 로그 보내기
                messagingTemplate.convertAndSend("/orin/sound-logs", convertToSoundLogDTO(savedData));
            }

        } catch (Exception e) {
            log.error("Error processing sound data: ", e);
        }
    }

    // New method
    private SoundLogDTO convertToSoundLogDTO(SoundData soundData) {
        SoundLogDTO dto = new SoundLogDTO();
        dto.setId(soundData.getId());
        dto.setSoundType(soundData.getSoundType());
        dto.setReceivedAt(soundData.getReceivedAt());
        return dto;
    }
}
