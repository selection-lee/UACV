package uacv.backend.hardware.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import uacv.backend.hardware.domain.enums.CommandType;

@Data
@ToString
@Builder
@AllArgsConstructor
public class CommandQueryDto {
    CommandType command;
    LocalDateTime sendDate;
}
