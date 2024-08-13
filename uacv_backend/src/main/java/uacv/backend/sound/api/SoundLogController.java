package uacv.backend.sound.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uacv.backend.sound.dto.SoundLogDTO;
import uacv.backend.sound.service.SoundDataService;

import java.util.List;

@RestController
@RequestMapping("/api/sound-logs")
public class SoundLogController {

    @Autowired
    private SoundDataService soundDataService;

    @GetMapping
    public List<SoundLogDTO> getSoundLogs() {
        return soundDataService.getAllSoundLogs();
    }
}
