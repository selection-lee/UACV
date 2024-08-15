package uacv.backend.hardware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.dto.CoordinateDto;
import uacv.backend.hardware.dto.LogResponseDto;
import uacv.backend.hardware.service.SendService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private SendService sendService;

    // 로그 기록 조회
    // 소리, 발사, 센서
    @RequestMapping(value = "/log/{eventType}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeviceLogs(
            @PathVariable("eventType") EventType eventType,
            @RequestParam(name = "command", required = false) CommandType commandType,
            @RequestParam(name = "page", defaultValue = "1") int pageCount) {

        try {
            List<?> queries = null;

            if (eventType == EventType.sensor) {
                queries = sendService.sendSensorLog(pageCount);
            } else if (eventType == EventType.command) {
                queries = sendService.sendCommandLog(commandType, pageCount);
            }

            LogResponseDto response = LogResponseDto.builder()
                    .eventType(eventType.toString())
                    .data(LogResponseDto.LogDataDto.builder()
                            .type(commandType == null ? eventType.toString() : commandType.toString())
                            .count(queries.size())
                            .queries(queries)
                            .build())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting {} logs: {}", eventType.toString(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 조종 명령 수신
     */
    @RequestMapping(value = "/control/{command}", method = RequestMethod.POST)
    public ResponseEntity<?> sendCommand(
            @PathVariable("command") CommandType command,
            @RequestBody CommandDto commandDto) {

        ControlDataDto controlDataDto = commandDto.getData();

        if (sendService.saveCommand(command, controlDataDto)) {
            try {
                if (command.toString() == "cannon") {
                    sendService.sendCommand("orin.cannon", commandDto);
                } else if (command.toString() == "steer") {
                    sendService.sendCommand("orin.steer", commandDto);
                } else if (command.toString() == "move") {
                    sendService.sendCommand("orin.throttle", commandDto);
                } else if (command.toString() == "fire") {
                    sendService.sendCommand("rpi.fire", commandDto);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                log.error("Error sending command: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("Request Data not inserted: {}", controlDataDto);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/connect")
    public CompletableFuture<String> hihi() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "안냥";
        });
    }

    @PostMapping("/coordinate")
    public ResponseEntity<?> sendCoordinate(@RequestBody CoordinateDto coordinateDto) {
        try {
            sendService.sendCoordinate(coordinateDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending coordinate: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
