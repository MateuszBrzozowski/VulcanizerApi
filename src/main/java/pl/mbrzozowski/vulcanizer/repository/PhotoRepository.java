package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mbrzozowski.vulcanizer.entity.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Transactional
    @Query("SELECT p FROM user u JOIN u.avatar p WHERE u.id = :userId")
    Optional<Photo> findByUserId(@Param("userId") Long userId);
}
