package uacv.backend.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.repository.SoundDataRepository;

import java.time.LocalDateTime;
import java.util.Map;

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
}
