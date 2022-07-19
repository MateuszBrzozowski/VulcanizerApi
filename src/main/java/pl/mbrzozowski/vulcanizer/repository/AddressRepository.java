package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mbrzozowski.vulcanizer.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE address a SET a.state = NULL WHERE a.id = :addressId")
    void deleteStateByAddressId(@Param("addressId") Long addressId);
}
