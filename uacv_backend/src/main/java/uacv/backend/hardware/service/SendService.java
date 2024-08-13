package uacv.backend.hardware.service;

import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.domain.enums.LogType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.ControlDataDto;

// 송신 서비스 인터페이스
public interface SendService {

    Boolean saveCommand(CommandType commandType, ControlDataDto controlDataDto);
    void sendCommand(String routingKey, CommandDto commandDto);
    void getDeviceLogs(LogType logType, EventType eventType, int pageCount);
}

