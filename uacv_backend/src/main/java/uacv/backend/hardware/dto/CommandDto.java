package uacv.backend.hardware.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uacv.backend.hardware.domain.enums.CommandType;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandDto {
    private CommandType commandType;
    private ControlDataDto data;
}
