package is.lab1.coordinates;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    // boolean existsByXAndY(Long x, Float y);
}
