package account.spring.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import account.spring.hardware.domain.SensorData;

public interface HardwareRepository extends MongoRepository<SensorData, Long> {

}
