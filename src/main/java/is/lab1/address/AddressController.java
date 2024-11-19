package is.lab1.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/addresses")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody AddressDto dto) {
        return new ResponseEntity<>(addressService.create(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Address> update(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.update(address), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public HttpStatus deleteAddress(@PathVariable("id") Long id) {
        addressService.delete(id);
        return HttpStatus.OK;
    }
}
