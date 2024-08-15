package uacv.backend.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uacv.backend.hardware.domain.ControlData;
import java.util.List;


public interface CommandRepository extends MongoRepository<ControlData, Long> {

    List<ControlData> findByCommand(String command);
}
