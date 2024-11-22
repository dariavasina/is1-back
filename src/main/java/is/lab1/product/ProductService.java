package is.lab1.product;

import is.lab1.address.Address;
import is.lab1.address.AddressRepository;
import is.lab1.coordinates.Coordinates;
import is.lab1.coordinates.CoordinatesMapper;
import is.lab1.coordinates.CoordinatesRepository;
import is.lab1.location.Location;
import is.lab1.location.LocationRepository;
import is.lab1.organization.Organization;
import is.lab1.organization.OrganizationMapper;
import is.lab1.organization.OrganizationRepository;
import is.lab1.person.Person;
import is.lab1.person.PersonMapper;
import is.lab1.person.PersonRepository;
import is.lab1.user.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import is.lab1.user.User;
import is.lab1.product.ProductMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;
//    private ProductMapper productMapper;
    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;
    private final OrganizationRepository organizationRepository;
    private final PersonRepository personRepository;
    private final CoordinatesRepository coordinatesRepository;


    @Autowired
    public ProductService(ProductRepository productRepository,
                          LocationRepository locationRepository, AddressRepository addressRepository,
                         OrganizationRepository organizationRepository, PersonRepository personRepository,
                         CoordinatesRepository coordinatesRepository) {
        this.productRepository = productRepository;
//        this.productMapper = productMapper;
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
        this.organizationRepository = organizationRepository;
        this.personRepository = personRepository;
        this.coordinatesRepository = coordinatesRepository;
    }

//    @Transactional
//    public void create(Product product) {
//        productRepository.save(product);
//    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

//    @Transactional
//    public Product create(ProductDto dto, User user) {
////        System.out.println("Entering ProductService.create()");
////        System.out.println("User: " + user);
////        System.out.println("Product DTO: " + dto);
//
//        final Product product = ProductMapper.toEntity(dto);
//        product.setUser(user);
//
//        try {
//            saveEmbedded(product);
//
//            return productRepository.save(product);
//        } catch (DataIntegrityViolationException e) {
//            System.err.println("DataIntegrityViolationException occurred: " + e.getMessage());
//            final Coordinates coordinates = product.getCoordinates();
//            System.out.println("Checking for existing coordinates: " + coordinates);
//
//            if (coordinatesRepository.existsByXAndY(coordinates.getX(), coordinates.getY())) {
//                System.err.println("Coordinates already exist: " + coordinates);
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordinates already exist");
//            }
//
//            System.err.println("Unknown DataIntegrityViolationException. Throwing generic error.");
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location already exists");
//        }
//    }
//
//    private void saveEmbedded(Product product) {
//        final Organization organization = product.getManufacturer();
//        final Address address = organization.getOfficialAddress();
//        final Person person = product.getOwner();
//
//        locationRepository.save(person.getLocation());
//        addressRepository.save(address);
//        organizationRepository.save(organization);
//        personRepository.save(person);
//        coordinatesRepository.save(product.getCoordinates());
//    }

    @Transactional
    public Product create(ProductDto dto, User user) {
        final Product product = ProductMapper.toEntity(dto);
        System.out.println(product);
        product.setUser(user);

        // Сохраняем или используем существующие вложенные сущности
        saveOrUseExistingEmbedded(product);

        // Сохраняем продукт
        return productRepository.save(product);
    }

    private void saveOrUseExistingEmbedded(Product product) {
        // Обработка координат
        final Coordinates coordinates = product.getCoordinates();
        if (coordinates != null) {
            System.out.println("Обрабатываем координаты: " + coordinates);
            Coordinates existingCoordinates = coordinatesRepository
                    .findByXAndY(coordinates.getX(), coordinates.getY())
                    .orElse(null);
            if (existingCoordinates != null) {
                System.out.println("Найдены существующие координаты: " + existingCoordinates);
                product.setCoordinates(existingCoordinates); // Используем существующие координаты
            } else {
                System.out.println("Координаты не найдены, создаем новые: " + coordinates);
                coordinatesRepository.save(coordinates); // Создаем новые координаты
            }
        }

        // Обработка организации
        Organization organization = product.getManufacturer();
        // Check if the organization is detached and merge it back if necessary
        if (organization != null) {
            System.out.println("Обрабатываем организацию: " + organization);
            Organization existingOrganization = organizationRepository
                    .findByName(organization.getName())
                    .orElse(null);
            if (existingOrganization != null) {
                System.out.println("Найдена существующая организация: " + existingOrganization);
                product.setManufacturer(existingOrganization); // Use the existing organization
            } else {
                System.out.println("Организация не найдена, обрабатываем адрес...");
                final Address address = organization.getOfficialAddress();
                if (address != null) {
                    Address existingAddress = addressRepository
                            .findByZipCode(address.getZipCode())
                            .orElse(null);
                    if (existingAddress != null) {
                        System.out.println("Найден существующий адрес: " + existingAddress);
                        organization.setOfficialAddress(existingAddress); // Use existing address
                    } else {
                        System.out.println("Адрес не найден, создаем новый: " + address);
                        addressRepository.save(address); // Save the new address
                    }
                }
                // Here, instead of save(), check if it's a new organization and then merge or save
                if (organization.getId() == null) {
                    // This is a new organization, so we can save it
                    System.out.println("Сохраняем новую организацию: " + organization);
                    organizationRepository.save(organization); // Save the new organization
                } else {
                    // If it has an ID but is detached, merge it back to the session
                    System.out.println("Мержим организацию: " + organization);
                    organization = organizationRepository.saveAndFlush(organization); // This will reattach it
                    product.setManufacturer(organization); // Use the merged organization
                }
            }
        }


        // Обработка владельца
        final Person person = product.getOwner();
        if (person != null) {
            System.out.println("Обрабатываем владельца: " + person);
            Person existingPerson = personRepository
                    .findByName(person.getName())
                    .orElse(null);
            if (existingPerson != null) {
                System.out.println("Найден существующий владелец: " + existingPerson);
                product.setOwner(existingPerson);
            } else {
                System.out.println("Владелец не найден, обрабатываем местоположение...");
                final Location location = person.getLocation();
                if (location != null) {
                    Location existingLocation = locationRepository
                            .findByXAndY(location.getX(), location.getY())
                            .orElse(null);
                    if (existingLocation != null) {
                        System.out.println("Найдено существующее местоположение: " + existingLocation);
                        person.setLocation(existingLocation);
                    } else {
                        System.out.println("Местоположение не найдено, создаем новое: " + location);
                        locationRepository.save(location);
                    }
                }
                System.out.println("Сохраняем нового владельца: " + person);
                personRepository.save(person);
            }
        }
    }






//    public Product update(Product product) {
//        return productRepository.save(product);
//    }

//    public void addNewProduct(Product product) {
//        Optional<Product> productOptional = productRepository.findByName(product.getName());
//        if (productOptional.isPresent()) {
//            throw new IllegalStateException("Product with name " + product.getName() + " already exists");
//        }
//        productRepository.save(product);
//    }

    public Product update(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getCoordinates() != null) {
            existingProduct.setCoordinates(product.getCoordinates());
        }

        if (product.getManufacturer() != null) {
            existingProduct.setManufacturer(product.getManufacturer());
        }

        return productRepository.save(existingProduct);
    }


    public void delete(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Product with id " + id + " does not exist");
        }
        productRepository.deleteById(id);
    }
}
