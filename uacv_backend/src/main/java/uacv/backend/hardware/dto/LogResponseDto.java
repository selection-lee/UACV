package uacv.backend.hardware.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LogResponseDto {
    private String eventType;
    private LogDataDto data;
    
    @Data
    @Builder
    @AllArgsConstructor
    public static class LogDataDto {
        private String type;
        private int count;
        private List<?> queries;
    }
}