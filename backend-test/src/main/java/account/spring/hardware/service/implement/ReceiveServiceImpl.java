package account.spring.hardware.service.implement;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import account.spring.hardware.repository.HardwareRepository;
import account.spring.hardware.service.ReceiveService;
import lombok.RequiredArgsConstructor;

// 수신 서비스 구현체
@Service
@RequiredArgsConstructor
public class ReceiveServiceImpl implements ReceiveService {
    
    // @Autowired
    // private HardwareRepository hardwareRepository;

    @Override
    @RabbitListener(queues = "test_queue")
    public void receiveMessage(Message message) {

        // ObjectMapper objectMapper = new ObjectMapper();
        // try {
        //     String jsonString = new String(message.getBody());
        //     SensorDataDto sensorData = objectMapper.readValue(jsonString, SensorDataDto.class);
        //     // hardwareRepository.save(sensorData);
        //     System.out.println("Received and saved sensor data: " + sensorData);
        // } catch (Exception e) {
        //     System.err.println("Error processing message: " + e.getMessage());
        // }
        String jsonString = new String(message.getBody());
        System.out.println("Received data: " + jsonString);
    }
}
