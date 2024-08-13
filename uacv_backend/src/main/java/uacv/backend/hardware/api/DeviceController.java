package uacv.backend.hardware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.domain.enums.LogType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.service.ReceiveService;
import uacv.backend.hardware.service.SendService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    // return entity;
    // }

    // 로그 기록 조회
    // 소리, 발사, 센서
    @RequestMapping(value = "/log/{logType}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeviceLogs(
            @PathVariable("logType") LogType logType,
            @RequestParam(name = "type", required = true) EventType eventType,
            @RequestParam(name = "page", defaultValue = "1") int pageCount) {

        sendService.getDeviceLogs(logType, eventType, pageCount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 조종 명령 수신
     */
//    @RequestMapping(value = "/control/{command}", method = RequestMethod.POST)
//    public ResponseEntity<?> sendCommand(
//            @PathVariable("command") CommandType command,
//            @RequestBody ControlDataDto controlDataDto) {
//
//
//        if (sendService.saveCommand(command, controlDataDto)) {
//            try {
//                if (command.toString() == "cannon") {
//                    sendService.sendCommand("orin.cannon", controlDataDto);
//                } else if (command.toString() == "steer") {
//                    sendService.sendCommand("orin.steer", controlDataDto);
//                } else if (command.toString() == "move") {
//                    sendService.sendCommand("orin.throttle", controlDataDto);
//                } else if (command.toString() == "fire") {
//                    sendService.sendCommand("rpi.fire", controlDataDto);
//                }
//                return new ResponseEntity<>(HttpStatus.OK);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        } else {
//            System.out.println("Request Data not inserted: " + controlDataDto);
//        }
//        ;
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @RequestMapping(value = "/control/{command}", method = RequestMethod.POST)
    public ResponseEntity<?> sendCommand(
            @PathVariable("command") CommandType command,
            @RequestBody CommandDto commandDto) {

        // CommandType과 ControlDataDto를 추출
        ControlDataDto controlDataDto = commandDto.getData();

        // 데이터를 저장 시도
        if (!sendService.saveCommand(command, controlDataDto)) {
            System.out.println("Request Data not inserted: " + controlDataDto);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // command에 따라 topic을 결정
            String topic = null;
            if (command.toString().equals("cannon")) {
                topic = "orin.cannon";
            } else if (command.toString().equals("steer")) {
                topic = "orin.steer";
            } else if (command.toString().equals("move")) {
                topic = "orin.throttle";
            } else if (command.toString().equals("fire")) {
                topic = "rpi.fire";
            }

            commandDto.setCommandType(command);

            // topic이 결정되었다면 명령을 전송
            if (topic != null) {
                sendService.sendCommand(topic, commandDto);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 후 서버 오류 상태 반환
            System.out.println("Error sending command: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/connect")
    public CompletableFuture<String> hihi() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1); // 30초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "안냥";
        });
    }
}
