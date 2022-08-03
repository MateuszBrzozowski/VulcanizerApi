package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.TokenCheckSum;
import pl.mbrzozowski.vulcanizer.entity.User;

@Repository
public interface TokenCheckSumRepository extends JpaRepository<TokenCheckSum, Long> {

    void deleteByUser(User user);
}
