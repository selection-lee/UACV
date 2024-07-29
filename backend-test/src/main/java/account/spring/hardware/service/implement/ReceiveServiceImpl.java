package account.spring.hardware.service.implement;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import account.spring.hardware.domain.SensorData;
import account.spring.hardware.dto.SensorDataDto;
import account.spring.hardware.repository.HardwareRepository;
import account.spring.hardware.service.ReceiveService;
import lombok.RequiredArgsConstructor;

// 수신 서비스 구현체
@Service
@RequiredArgsConstructor
public class ReceiveServiceImpl implements ReceiveService {

    @Autowired
    private HardwareRepository hardwareRepository;

    @Override
    @RabbitListener(queues = "sensor_queue")
    public void receiveMessage(Message message) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = new String(message.getBody());
            SensorDataDto sensorDataDto = objectMapper.readValue(jsonString, SensorDataDto.class);
            SensorDataDto savedDto = createSensor(sensorDataDto);
            System.out.println("Received and saved sensor data: " + savedDto);
        } catch (JsonMappingException e) {
            System.err.println("Error mapping JSON: {}" + e.getMessage());
            System.err.println("Raw message content: {}" + message.getBody());
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: {}" + e.getMessage());
            System.err.println("Raw message content: {}" + message.getBody());
        } catch (Exception e) {
            System.err.println("Unexpected error processing message: {}" + e.getMessage());
            System.err.println("Raw message content: {}" + message.getBody());
        }
    }

    // public List<SensorDataDto> getAllSensors() {
    //     return hardwareRepository.findAll().stream()
    //             .map(this::convertToDto)
    //             .collect(Collectors.toList());
    // }

    // public SensorDataDto getSensorById(String id) {
    //     return hardwareRepository.findById(id)
    //             .map(this::convertToDto)
    //             .orElse(null);
    // }

    public SensorDataDto createSensor(SensorDataDto sensorDataDto) {
        // SensorDataDto sensorDataDto = new SensorDataDto();
        SensorData sensorData = convertToEntity(sensorDataDto);
        SensorData savedData = hardwareRepository.insert(sensorData);
        return convertToDto(savedData);
    }

    /*
    public SensorDataDto updateSensor(String id, SensorDataDto sensorDataDto) {
        if (hardwareRepository.existsById(id)) {
            Sensor sensor = convertToEntity(sensorDataDto);
            sensor.setId(id);
            Sensor updatedSensor = hardwareRepository.save(sensor);
            return convertToDto(updatedSensor);
        }
        return null;

    public void deleteSensor(String id) {
        hardwareRepository.deleteById(id);
    }
    */

    // private SensorDataDto convertToDto(SensorData sensorData) {
    //     SensorDataDto sensorDataDto = new SensorDataDto();

    //     sensorDataDto.setDeviceId(sensorData.getDeviceId());
    //     sensorDataDto.setChassisDir(sensorData.getChassisDir());
    //     sensorDataDto.setCannonDir(sensorData.getCannonDir());
    //     sensorDataDto.setThrottleValue(sensorData.getThrottleValue());
    //     sensorDataDto.setTemperature(sensorData.getTemperature());
        
    //     return sensorDataDto;
    // }

    private SensorDataDto convertToDto(SensorData sensorData) {
        SensorDataDto sensorDataDto = new SensorDataDto();

        // sensorDataDto.setDeviceId(sensorData.getDeviceId());
        sensorDataDto.setChassisDir(sensorData.getChassisDir());
        sensorDataDto.setCannonDir(sensorData.getCannonDir());
        sensorDataDto.setThrottleValue(sensorData.getThrottleValue());
        sensorDataDto.setTemperature(sensorData.getTemperature());
        sensorDataDto.setReceivedDate(LocalDateTime.now());
        
        return sensorDataDto;
    }
    
    private SensorData convertToEntity(SensorDataDto sensorDataDto) {
        SensorData sensorData = new SensorData();
        // sensorData.setDeviceId(sensorDataDto.getDeviceId());
        sensorData.setChassisDir(sensorDataDto.getChassisDir());
        sensorData.setCannonDir(sensorDataDto.getCannonDir());
        sensorData.setThrottleValue(sensorDataDto.getThrottleValue());
        sensorData.setTemperature(sensorDataDto.getTemperature());
        sensorData.setReceivedDate(sensorDataDto.getReceivedDate());

        return sensorData;
    }
}
