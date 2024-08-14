package uacv.backend.hardware.service;

import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.domain.enums.LogType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.dto.CoordinateDto;

// 송신 서비스 인터페이스
public interface SendService {

    Boolean saveCommand(CommandType commandType, ControlDataDto controlDataDto);
    void sendCommand(String routingKey, CommandDto commandDto);
    void getDeviceLogs(LogType logType, EventType eventType, int pageCount);

    // Why: 새로운 메서드 추가
    // What: 좌표 데이터 전송을 위한 메서드
    void sendCoordinate(CoordinateDto coordinateDto);
}

