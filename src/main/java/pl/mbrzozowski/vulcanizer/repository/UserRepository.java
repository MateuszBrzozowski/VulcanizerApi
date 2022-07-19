package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE user u SET u.phone = NULL WHERE u.id = :userId")
    void deletePhoneByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE user u SET u.address = NULL WHERE u.id = :userId")
    void deleteAddressByUserID(@Param("userId") Long userId);
}
