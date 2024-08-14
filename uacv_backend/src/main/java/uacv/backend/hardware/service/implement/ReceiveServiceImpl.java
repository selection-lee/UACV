package uacv.backend.hardware.service.implement;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoWriteException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uacv.backend.hardware.domain.SensorData;
import uacv.backend.hardware.dto.SensorDataDto;
import uacv.backend.hardware.repository.SensorDataRepository;
import uacv.backend.hardware.service.ReceiveService;

// 수신 서비스 구현체
@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveServiceImpl implements ReceiveService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Override
    @RabbitListener(queues = "sensor_queue")
    public void receiveMessage(SensorDataDto sensorDataDto) throws Exception {
        log.debug("Received Message from sensor_queue: {}", sensorDataDto);
        SensorData insertedData = insertSensorData(sensorDataDto);

        if (insertedData != null) {
            log.debug("Saved sensor data: {}", insertedData);
        }
    }

    // public List<SensorDataDto> getAllSensors() {
    //     return sensorDataRepository.findAll().stream()
    //             .map(this::convertToDto)
    //             .collect(Collectors.toList());
    // }

    // public SensorDataDto getSensorById(String id) {
    //     return sensorDataRepository.findById(id)
    //             .map(this::convertToDto)
    //             .orElse(null);
    // }

    public SensorData insertSensorData(SensorDataDto sensorDataDto) {
        SensorData insertedData = null;

        try {
            insertedData = sensorDataRepository.insert(SensorData.builder()
                    .heading(sensorDataDto.getHeading())
                    .direction(sensorDataDto.getDirection())
                    .accelerometer(
                            SensorData.AxisData.builder()
                                    .x(sensorDataDto.getAccelerometer().getX())
                                    .y(sensorDataDto.getAccelerometer().getY())
                                    .z(sensorDataDto.getAccelerometer().getZ())
                                    .build())
                    .gyroscope(
                            SensorData.AxisData.builder()
                                    .x(sensorDataDto.getGyroscope().getX())
                                    .y(sensorDataDto.getGyroscope().getY())
                                    .z(sensorDataDto.getGyroscope().getZ())
                                    .build())
                    .build());
        } catch (MongoWriteException e) {
            log.error("Error inserting Sensor Data: {}", e.getMessage());
        }
        return insertedData;
    }
}
