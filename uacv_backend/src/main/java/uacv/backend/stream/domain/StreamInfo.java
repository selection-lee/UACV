package uacv.backend.stream.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class StreamInfo {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 일단 자동증가하게
    private Long id;

    private String streamURL;
    private String streamName;
    private String status; // 스트림 현재 상태(active, inactive,,, )
}
