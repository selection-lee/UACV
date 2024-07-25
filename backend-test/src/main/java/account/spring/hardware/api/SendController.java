package account.spring.hardware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import account.spring.hardware.dto.ControlDataDto;
import account.spring.hardware.service.SendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/mqtt")
public class SendController {
    
    @Autowired
    private SendService sendService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ControlDataDto controlDataDto) {
        sendService.sendMessage(controlDataDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
