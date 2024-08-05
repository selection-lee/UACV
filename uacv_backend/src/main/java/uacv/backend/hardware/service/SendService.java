package uacv.backend.hardware.service;

import uacv.backend.hardware.dto.ControlDataDto;

// 송신 서비스 인터페이스
public interface SendService {

    void sendCommand(String targetDevice, ControlDataDto controlDataDto);
}
