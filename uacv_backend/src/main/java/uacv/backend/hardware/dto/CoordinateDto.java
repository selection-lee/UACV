package uacv.backend.hardware.dto;

import lombok.Data;

@Data
public class CoordinateDto {
    private Header header;
    private Pose pose;

    @Data
    public static class Header {
        private String frame_id;
    }

    @Data
    public static class Pose {
        private Position position;
        private Orientation orientation;
    }

    @Data
    public static class Position {
        private int x;
        private int y;
        private int z;
    }

    @Data
    public static class Orientation {
        private int x;
        private int y;
        private int z;
        private int w;
    }
}

