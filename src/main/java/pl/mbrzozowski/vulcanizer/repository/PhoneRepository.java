package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.Phone;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByNumber(String number);
}
