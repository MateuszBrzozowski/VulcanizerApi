package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.ConfirmationToken;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Override
    @Modifying
    @Query("DELETE FROM confirm_token ct WHERE ct.id = :id")
    void deleteById(@Param("id") Long aLong);  //bez override potrafi nie usunac. Nie wiem czemu.

    Optional<ConfirmationToken> findByToken(String token);

    Optional<ConfirmationToken> findByUser(User user);
}
