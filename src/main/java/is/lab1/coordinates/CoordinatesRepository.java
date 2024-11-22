package is.lab1.coordinates;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
     boolean existsByXAndY(Long x, Double y);
     Optional<Coordinates> findByXAndY(Long x, Double y);
}
