package uacv.backend.sound.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.repository.SoundDataRepository;

import java.util.List;

@Controller
public class SoundDataController {

    @Autowired
    private SoundDataRepository soundDataRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public void broadcastSoundData() {
        List<SoundData> soundDataList = soundDataRepository.findAll();
        soundDataList.forEach(soundData ->
                template.convertAndSend("/orin/sensor", soundData));
    }
}
