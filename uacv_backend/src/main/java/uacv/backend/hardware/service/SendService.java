package uacv.backend.hardware.service;

import java.util.List;

import uacv.backend.hardware.domain.enums.CommandType;
// import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.CommandQueryDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.dto.CoordinateDto;
import uacv.backend.hardware.dto.SensorDataQueryDto;

// 송신 서비스 인터페이스
public interface SendService {

    Boolean saveCommand(CommandType commandType, ControlDataDto controlDataDto);
    void sendCommand(String routingKey, CommandDto commandDto);
    // void getDeviceLogs(LogType logType, EventType eventType, int pageCount);
    void sendCoordinate(CoordinateDto coordinateDto);
    SensorDataQueryDto sendSensorLog(int pageCount);
    List<CommandQueryDto> sendCommandLog(CommandType commandType, int pageCount);
}

