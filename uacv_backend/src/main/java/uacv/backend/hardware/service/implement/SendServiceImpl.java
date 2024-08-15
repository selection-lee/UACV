package uacv.backend.hardware.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mongodb.MongoWriteException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uacv.backend.hardware.domain.ControlData;
import uacv.backend.hardware.domain.enums.CommandType;
import uacv.backend.hardware.dto.CommandDto;
import uacv.backend.hardware.dto.CommandQueryDto;
import uacv.backend.hardware.dto.ControlDataDto;
import uacv.backend.hardware.dto.CoordinateDto;
import uacv.backend.hardware.dto.SensorDataQueryDto;
import uacv.backend.hardware.repository.CommandRepository;
import uacv.backend.hardware.repository.SensorDataRepository;
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
    private SensorDataRepository sensorDataRepository;

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

    public Boolean insertControlData(CommandType commandType, ControlDataDto controlDataDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Auth info: {}", authentication);

        try {
            commandRepository.insert(ControlData.builder()
                    .command(commandType)
                    .commander(authentication.getName())
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

    @Override
    public void sendCoordinate(CoordinateDto coordinateDto) {
        log.debug("Sending coordinate: {}", coordinateDto);
        rabbitTemplate.convertAndSend(topicExchange.getName(), "orin.move", coordinateDto);
    }

    @Override
    public List<CommandQueryDto> sendCommandLog(CommandType commandType, int pageCount) {
        if (commandType == CommandType.all) {
            return commandRepository.findAll().stream()
                    .map(commandLog -> CommandQueryDto.builder()
                            .command(commandLog.getCommand())
                            .commander(commandLog.getCommander())
                            .controlData(ControlDataDto.builder()
                                    .cannon_x(commandLog.getCannon_x())
                                    .cannon_y(commandLog.getCannon_y())
                                    .fire(commandLog.getFire())
                                    .move(commandLog.getMove())
                                    .steer(commandLog.getSteer())
                                    .build())
                            .sendDate(commandLog.getSendDate())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return commandRepository.findByCommand(commandType.toString()).stream()
                    .map(commandLog -> CommandQueryDto.builder()
                            .command(commandLog.getCommand())
                            .commander(commandLog.getCommander())
                            .controlData(ControlDataDto.builder()
                                    .cannon_x(commandLog.getCannon_x())
                                    .cannon_y(commandLog.getCannon_y())
                                    .fire(commandLog.getFire())
                                    .move(commandLog.getMove())
                                    .steer(commandLog.getSteer())
                                    .build())
                            .sendDate(commandLog.getSendDate())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<SensorDataQueryDto> sendSensorLog(int pageCount) {
        return sensorDataRepository.findAll().stream()
                .map(sensorLog -> SensorDataQueryDto.builder()
                        .heading(sensorLog.getHeading())
                        .direction(sensorLog.getDirection())
                        .receivedDate(sensorLog.getReceivedDate())
                        .accelerometer(
                                SensorDataQueryDto.AxisData.builder()
                                        .x(sensorLog.getAccelerometer().getX())
                                        .y(sensorLog.getAccelerometer().getY())
                                        .z(sensorLog.getAccelerometer().getZ())
                                        .build())
                        .gyroscope(
                                SensorDataQueryDto.AxisData.builder()
                                        .x(sensorLog.getGyroscope().getX())
                                        .y(sensorLog.getGyroscope().getY())
                                        .z(sensorLog.getGyroscope().getZ())
                                        .build())
                        .build())
                .collect(Collectors.toList());
    }
}
