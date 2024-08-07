package uacv.backend.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uacv.backend.hardware.domain.ControlData;

public interface CommandRepository extends MongoRepository<ControlData, Long> {

}
