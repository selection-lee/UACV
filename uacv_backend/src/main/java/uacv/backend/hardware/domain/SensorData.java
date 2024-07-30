package uacv.backend.hardware.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sensor_data")
public class SensorData {
    // @Id
    // private Long deviceId;

    @CreatedDate
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
