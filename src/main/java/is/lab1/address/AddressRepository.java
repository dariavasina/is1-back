package is.lab1.address;

import is.lab1.coordinates.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    Optional<Address> findByZipCode(String zipCode);
}


