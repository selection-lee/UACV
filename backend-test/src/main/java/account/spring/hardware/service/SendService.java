package account.spring.hardware.service;

import account.spring.hardware.dto.ControlDataDto;

// 송신 서비스 인터페이스
public interface SendService {
    
    void sendMessage(ControlDataDto controlDataDto);
}