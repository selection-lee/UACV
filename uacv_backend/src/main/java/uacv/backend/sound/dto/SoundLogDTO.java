package uacv.backend.sound.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SoundLogDTO {
    private Long id;
    private String soundType;
    private LocalDateTime receivedAt;

}
