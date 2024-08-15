package uacv.backend.hardware.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class SensorDataQueryDto {

    private float heading;
    private String direction;
    private AxisData accelerometer;
    private AxisData gyroscope;

    @Data
    @Builder
    @AllArgsConstructor
    public static class AxisData {
        @JsonProperty("X")
        private float x;
        @JsonProperty("Y")
        private float y;
        @JsonProperty("Z")
        private float z;
    }

    private LocalDateTime receivedDate;
}
