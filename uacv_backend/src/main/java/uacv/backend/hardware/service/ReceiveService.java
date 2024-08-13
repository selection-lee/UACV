package uacv.backend.hardware.service;

import uacv.backend.hardware.dto.SensorDataDto;

public interface ReceiveService {

    void receiveMessage(SensorDataDto sensorDataDto) throws Exception;
}
