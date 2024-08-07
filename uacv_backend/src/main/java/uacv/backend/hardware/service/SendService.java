package uacv.backend.hardware.service;

import uacv.backend.hardware.domain.EventType;
import uacv.backend.hardware.domain.LogType;
import uacv.backend.hardware.dto.ControlDataDto;

// 송신 서비스 인터페이스
public interface SendService {

    void sendCommand(String routingKey, ControlDataDto controlDataDto);
    void getDeviceLogs(LogType logType, EventType eventType, int pageCount);
}

