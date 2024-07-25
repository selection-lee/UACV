package account.spring.hardware.dto;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorDataDto {

    // 차체 방향
    private Float chassisDir;
    // 포신 방향
    private Float cannonDir;
    // DC 모터 제어값
    private Float throttleValue;
    // 온습도
    private Float tempeature;
    // 라이다 / 맵핑 데이터
    @CreationTimestamp
    private Date receivedDate;
    
    @Builder
    public SensorDataDto(Float chassisDir, Float cannonDir, Float tempeature, Float throttleValue, Date receivedDate) {
        this.chassisDir = chassisDir;
        this.cannonDir = cannonDir;
        this.tempeature = tempeature;
        this.throttleValue = throttleValue;
        this.receivedDate = receivedDate;
    }
}
