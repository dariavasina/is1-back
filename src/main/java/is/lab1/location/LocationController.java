package is.lab1.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationDto dto) {
        return new ResponseEntity<>(locationService.create(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Location> update(@RequestBody Location location) {
        System.out.println("LocationController.update");
        return new ResponseEntity<>(locationService.update(location), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public HttpStatus deleteLocation(@PathVariable("id") Long id) {
        locationService.delete(id);
        return HttpStatus.OK;
    }
}
