package uacv.backend.hardware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uacv.backend.hardware.domain.EventType;
import uacv.backend.hardware.domain.LogType;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.service.ReceiveService;
import uacv.backend.hardware.service.SendService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private ReceiveService receiveService;

    @Autowired
    private SendService sendService;

    // 센서 정보 수신?
    // @PostMapping("/receive")
    // public String postMethodName(@RequestBody String entity) {
    //     return entity;
    // }

    // 로그 기록 조회
    // 소리, 발사, 센서
    // @GetMapping("/logs")
    @RequestMapping(value = "/log/{logType}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeviceLogs(
            @PathVariable("logType") LogType logType,
            @RequestParam(name = "type", required = true) EventType eventType,
            @RequestParam(name = "page", defaultValue = "1") int pageCount) {
        
        sendService.getDeviceLogs(logType, eventType, pageCount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 조종 명령 송신
    @PostMapping("/control")
    public ResponseEntity<?> sendCommand(@RequestBody String targetDevice, ControlDataDto controlDataDto) {
        sendService.sendCommand(targetDevice, controlDataDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 조종 명령 수행 완료 수신

}
