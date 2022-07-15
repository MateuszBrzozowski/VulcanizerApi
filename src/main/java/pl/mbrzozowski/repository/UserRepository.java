package pl.mbrzozowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.entity.User;

@Repository
public abstract class UserRepository implements JpaRepository<User, Long> {
}
