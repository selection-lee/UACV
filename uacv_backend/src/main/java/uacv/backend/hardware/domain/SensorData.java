package uacv.backend.hardware.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "sensor_data")
public class SensorData {

    @CreatedDate
    private LocalDateTime receivedDate;

    private float heading;
    private String direction;
    private AxisData accelerometer;
    private AxisData gyroscope;

    @Data
    @Builder
    public static class AxisData {
        private float x;
        private float y;
        private float z;
    }
}
