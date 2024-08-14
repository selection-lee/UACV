package uacv.backend.hardware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SensorDataDto {

    @JsonProperty("Heading")
    private float heading;
    @JsonProperty("Direction")
    private String direction;
    @JsonProperty("Accelerometer")
    private AxisData accelerometer;
    @JsonProperty("Gyroscope")
    private AxisData gyroscope;

    @Data
    public static class AxisData {
        @JsonProperty("X")
        private float x;
        @JsonProperty("Y")
        private float y;
        @JsonProperty("Z")
        private float z;
    }
}
