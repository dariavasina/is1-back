package is.lab1.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location create(LocationDto dto) {
        Location location = Location.builder()
                .id(dto.getId())
                .x(dto.getX())
                .y(dto.getY())
                .name(dto.getName())
                .build();
        return locationRepository.save(location);
    }

    public Location update(Location location) {
        System.out.printf("LocationService.update: %s%n", location);
        return locationRepository.save(location);
    }

    public void delete(Long id) {
        boolean exists = locationRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Location with id " + id + " does not exist");
        }
        locationRepository.deleteById(id);
    }
}
