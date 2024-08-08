package uacv.backend.hardware.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ControlDataDto {

    // 명령 Parameter
    private String fire;
    private String move;
    private int cannon_x;
    private int cannon_y;
    private int steer;
}
