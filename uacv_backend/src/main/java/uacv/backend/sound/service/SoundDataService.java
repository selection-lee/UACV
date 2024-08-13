package uacv.backend.sound.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.dto.SoundLogDTO;
import uacv.backend.sound.repository.SoundDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SoundDataService {

    @Autowired
    private SoundDataRepository soundDataRepository;

    // EDIT: Added SimpMessagingTemplate
    // WHY: To enable broadcasting of new log entries
    // WHAT: New autowired dependency
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    public SoundData saveSoundData(SoundData soundData) {

//        return soundDataRepository.save(soundData);
        SoundData savedData = soundDataRepository.save(soundData);
        // EDIT: Added call to broadcast new log
        // WHY: To ensure real-time updates are sent when new data is saved
        // WHAT: New method call after saving data
        broadcastNewLog(savedData);
        return savedData;
    }

//    @RabbitListener(queues = "${spring.rabbitmq.queues.sensor.name}")
//    public void receiveSoundData(Map<String, Map<String , String >> data) {
//        String soundType = data.get("sound").get("type");
//        SoundData soundData = new SoundData();
//        soundData.setSoundType(soundType);
//        soundData.setReceivedAt(LocalDateTime.now());
//
//        soundDataRepository.save(soundData);
//    }

    // New method
    public List<SoundLogDTO> getAllSoundLogs() {
        return soundDataRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // New method
    private SoundLogDTO convertToDTO(SoundData soundData) {
        SoundLogDTO dto = new SoundLogDTO();
        dto.setId(soundData.getId());
        dto.setSoundType(soundData.getSoundType());
        dto.setReceivedAt(soundData.getReceivedAt());
        return dto;
    }

    // EDIT: Added method to broadcast new log
    // WHY: To send real-time updates when new sound data is saved
    // WHAT: New private method for broadcasting
    private void broadcastNewLog(SoundData soundData) {
        SoundLogDTO logDTO = convertToDTO(soundData);
        messagingTemplate.convertAndSend("/orin/sound-logs", logDTO);
    }
}