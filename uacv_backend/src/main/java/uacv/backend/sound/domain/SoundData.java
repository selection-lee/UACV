package uacv.backend.sound.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sound_data")
public class SoundData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String soundType;

    private LocalDateTime receivedAt;

    public SoundData(String soundType) {
        this.soundType = soundType;
        this.receivedAt = LocalDateTime.now();
    }
}
