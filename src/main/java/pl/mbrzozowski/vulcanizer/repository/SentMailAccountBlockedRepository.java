package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.SentMailAccountBlocked;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.Optional;

@Repository
public interface SentMailAccountBlockedRepository extends JpaRepository<SentMailAccountBlocked, Long> {

    Optional<SentMailAccountBlocked> findByUser(User user);

    void deleteByUser(User user);

//    @Modifying
//    @Query("DELETE FROM sent_mail_account_blocked smab WHERE smab.user = :user_id")
//    void deleteByUser(@Param("user_id") Long aLong);
}
