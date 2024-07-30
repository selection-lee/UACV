package uacv.backend.hardware.dto;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ControlDataDto {
    
    // 명령어
    // 명령어 유지 시간
    // 즉시 정지?
    // 명령 생성 시간
    @CreationTimestamp
    private Date sendDate;
}
