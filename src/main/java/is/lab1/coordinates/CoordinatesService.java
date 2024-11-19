package is.lab1.coordinates;

import is.lab1.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatesService {

    private final CoordinatesRepository coordinatesRepository;

    @Autowired
    public CoordinatesService(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    List<Coordinates> getAllCoordinates() {
        return coordinatesRepository.findAll();
    }

    public Coordinates create(CoordinatesDto dto) {
        Coordinates coordinates = Coordinates.builder()
                .id(dto.getId())
                .x(dto.getX())
                .y(dto.getY())
                .build();
        return coordinatesRepository.save(coordinates);
    }

    public Coordinates update(Coordinates coordinates) {
        return coordinatesRepository.save(coordinates);
    }

    public void delete(Long id) {
        boolean exists = coordinatesRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Coordinates with id " + id + " do not exist");
        }
        coordinatesRepository.deleteById(id);
    }
}
