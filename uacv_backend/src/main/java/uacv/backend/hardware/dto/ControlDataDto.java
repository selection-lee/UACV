package uacv.backend.hardware.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class ControlDataDto {

    // 명령 Parameter
    private String fire;
    private String move;
    private int cannon_x;
    private int cannon_y;
    private int steer;
}
