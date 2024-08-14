package uacv.backend.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uacv.backend.hardware.domain.SensorData;

public interface SensorDataRepository extends MongoRepository<SensorData, Long> {

}
