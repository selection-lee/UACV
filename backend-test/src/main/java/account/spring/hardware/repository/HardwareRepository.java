package account.spring.hardware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import account.spring.hardware.domain.Hardware;

public interface HardwareRepository extends MongoRepository<Hardware, Long> {

}
