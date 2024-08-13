package uacv.backend.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public SoundData saveSoundData(SoundData soundData) {
        return soundDataRepository.save(soundData);
    }

//    @RabbitListener(queues = "${spring.rabbitmq.queues.sensor.name}")
    public void receiveSoundData(Map<String, Map<String , String >> data) {
        String soundType = data.get("sound").get("type");
        SoundData soundData = new SoundData();
        soundData.setSoundType(soundType);
        soundData.setReceivedAt(LocalDateTime.now());

        soundDataRepository.save(soundData);
    }

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
}
