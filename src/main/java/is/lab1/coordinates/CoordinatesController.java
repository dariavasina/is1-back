package is.lab1.coordinates;


import is.lab1.product.Product;
import is.lab1.product.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/coordinates")
public class CoordinatesController {
    private final CoordinatesService coordinatesService;

    @Autowired
    public CoordinatesController(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @GetMapping
    public List<Coordinates> getCoordinates() {
        return coordinatesService.getAllCoordinates();
    }

    @PostMapping
    public ResponseEntity<Coordinates> create(@RequestBody CoordinatesDto dto) {
        return new ResponseEntity<>(coordinatesService.create(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Coordinates> update(@RequestBody Coordinates coordinates) {
        return new ResponseEntity<>(coordinatesService.update(coordinates), HttpStatus.OK);
    }

    @DeleteMapping(path ="{id}")
    public HttpStatus deleteCoordinates(@PathVariable("id") Long id) {
        coordinatesService.delete(id);
        return HttpStatus.OK;
    }
}
