package uacv.backend.hardware.service.implement;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoWriteException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uacv.backend.hardware.domain.ControlData;
import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.domain.enums.EventType;
import uacv.backend.hardware.domain.enums.LogType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.repository.CommandRepository;
import uacv.backend.hardware.service.SendService;

// 송신 서비스 구현체
@Slf4j
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private TopicExchange topicExchange;

    @Override
    public Boolean saveCommand(CommandType commandType, ControlDataDto controlDataDto) {
        return insertControlData(commandType, controlDataDto);
    }

    @Override
    public void sendCommand(String routingKey, CommandDto commandDto) {
        log.debug("Routing Key: {}, Command Data: {}", routingKey, commandDto);
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, commandDto);
    }

    @Override
    public void getDeviceLogs(LogType logType, EventType eventType, int pageCount) {

    }

    public Boolean insertControlData(CommandType commandType, ControlDataDto controlDataDto) {
        try {
            commandRepository.insert(ControlData.builder()
                    .command(commandType)
                    .fire(controlDataDto.getFire())
                    .cannon_x(controlDataDto.getCannon_x())
                    .cannon_y(controlDataDto.getCannon_y())
                    .steer(controlDataDto.getSteer())
                    .move(controlDataDto.getMove())
                    .build());
            return true;
        } catch (MongoWriteException e) {
            log.error("Error inserting Control Data: {}", e.getMessage());
            return false;
        }
    }
}
