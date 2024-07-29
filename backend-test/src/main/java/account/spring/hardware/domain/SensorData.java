package account.spring.hardware.domain;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sensor_data")
public class SensorData {
    // @Id
    // private Long deviceId;

    // DB 삽입 일자
    // @CreationTimestamp
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
