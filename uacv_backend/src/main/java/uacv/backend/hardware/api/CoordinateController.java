package uacv.backend.hardware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import uacv.backend.hardware.dto.CoordinateDto;
import uacv.backend.hardware.service.SendService;


@Slf4j
@RestController
@RequestMapping("/api")
public class CoordinateController {

    @Autowired
    private SendService sendService;

    @PostMapping("/coordinate")
    public ResponseEntity<?> sendCoordinate(@RequestBody CoordinateDto coordinateDto) {
        try {
            // Why: 좌표 데이터를 RabbitMQ로 전송
            // What: 'orin/move' 토픽으로 좌표 데이터 전송
            sendService.sendCoordinate(coordinateDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending coordinate: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}