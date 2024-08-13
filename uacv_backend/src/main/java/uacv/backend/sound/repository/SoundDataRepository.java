package uacv.backend.sound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uacv.backend.sound.domain.SoundData;

public interface SoundDataRepository extends JpaRepository<SoundData, Long> {
}