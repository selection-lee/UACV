package uacv.backend.sound.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import uacv.backend.sound.domain.SoundData;
import uacv.backend.sound.dto.SoundDataDTO;
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

    @MessageMapping("socket/sound")
    @SendTo("/orin/sensor")
    public String handleSoundMessage(SoundDataDTO soundData) {
        // Process the incoming sound data
        System.out.println("Received sound data: " + soundData.getType());

        // Save to repository if needed
        SoundData newSoundData = new SoundData(soundData.getType());
        soundDataRepository.save(newSoundData);

        // Return a message that will be sent to subscribers
        return "Processed sound: " + soundData.getType();
    }
}
