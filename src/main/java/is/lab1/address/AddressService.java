package is.lab1.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address create(AddressDto dto) {
        Address address = Address.builder()
                .id(dto.getId())
                .zipCode(dto.getZipCode())
                .build();
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        boolean exists = addressRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Address with id " + id + " does not exist");
        }
        addressRepository.deleteById(id);
    }
}
