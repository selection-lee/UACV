package uacv.backend.hardware.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import uacv.backend.hardware.domain.enums.CommandType;

@Data
@Builder
@Document(collection = "control_log")
public class ControlData {

    // 명령
    private CommandType command;

    // 명령 Parameter
    @Builder.Default
    private String fire = "off";
    private String move;
    private int cannon_x;
    private int cannon_y;
    private int steer;

    // 명령 생성 시간
    @CreatedDate
    private LocalDateTime sendDate;
}
