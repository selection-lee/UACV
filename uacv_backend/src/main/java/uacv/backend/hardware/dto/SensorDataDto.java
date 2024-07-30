package uacv.backend.hardware.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SensorDataDto {

    // private Long deviceId;
    private LocalDateTime receivedDate;

    // 차체 방향
    private Float chassisDir;
    // 포신 방향
    private Float cannonDir;
    // DC 모터 제어값
    private Float throttleValue;
    // 온습도
    private Float temperature;
    // TODO
    // 라이다 / 맵핑 데이터

}
