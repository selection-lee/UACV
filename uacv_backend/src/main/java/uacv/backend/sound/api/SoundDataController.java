package uacv.backend.sound.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.dto.SoundDataDTO;
import uacv.backend.sound.repository.SoundDataRepository;

import java.util.List;

@Slf4j
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

    @MessageMapping("socket/sound")
    @SendTo("/orin/sensor")
    public String handleSoundMessage(SoundDataDTO soundData) {
        log.debug("Received sound data: {}", soundData.getType());

        SoundData newSoundData = new SoundData(soundData.getType());
        soundDataRepository.save(newSoundData);

        return "Processed sound: " + soundData.getType();
    }
}
