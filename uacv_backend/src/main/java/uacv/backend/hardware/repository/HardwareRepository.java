package uacv.backend.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uacv.backend.hardware.domain.SensorData;

public interface HardwareRepository extends MongoRepository<SensorData, Long> {

}
