package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.ResetPasswordToken;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    @Override
    @Modifying
    @Query("DELETE FROM reset_password_token rt WHERE rt.id = :id")
    void deleteById(@Param("id") Long aLong);  //bez override potrafi nie usunac. Nie wiem czemu.

    Optional<ResetPasswordToken> findByToken(String token);

    Optional<ResetPasswordToken> findByUser(User user);
}
