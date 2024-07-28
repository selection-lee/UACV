package account.spring.hardware.dto;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @RequiredArgsConstructor
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

    // public SensorDataDto(Long deviceId, Float chassisDir, Float cannonDir, Float throttleValue, Float tempeature) {
    //     this.deviceId = deviceId;
    //     this.chassisDir = chassisDir;
    //     this.cannonDir = cannonDir;
    //     this.throttleValue = throttleValue;
    //     this.tempeature = tempeature;
    // }
}
