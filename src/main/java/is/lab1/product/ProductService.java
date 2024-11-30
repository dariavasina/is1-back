package is.lab1.product;

import is.lab1.address.Address;
import is.lab1.address.AddressRepository;
import is.lab1.coordinates.Coordinates;
import is.lab1.coordinates.CoordinatesRepository;
import is.lab1.location.Location;
import is.lab1.location.LocationRepository;
import is.lab1.location.LocationService;
import is.lab1.organization.Organization;
import is.lab1.organization.OrganizationRepository;
import is.lab1.person.Person;
import is.lab1.person.PersonRepository;
import is.lab1.person.PersonService;
import is.lab1.user.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import is.lab1.user.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
//    private ProductMapper productMapper;
    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;
    private final OrganizationRepository organizationRepository;
    private final PersonRepository personRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final ProductSpecificationService productSpecificationService;



    @Autowired
    public ProductService(ProductRepository productRepository,
                          LocationRepository locationRepository, AddressRepository addressRepository,
                         OrganizationRepository organizationRepository, PersonRepository personRepository,
                         CoordinatesRepository coordinatesRepository, ProductSpecificationService productSpecificationService) {
        this.productRepository = productRepository;
//        this.productMapper = productMapper;
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
        this.organizationRepository = organizationRepository;
        this.personRepository = personRepository;
        this.coordinatesRepository = coordinatesRepository;
        this.productSpecificationService = productSpecificationService;

    }

//    public List<Product> getProducts() {
//        return productRepository.findAll();
//    }


    public List<ProductDto> getProducts(Pageable paging, ProductFilter productFilter) {
        final Specification<Product> spec = productSpecificationService.filterBy(productFilter);
        final Page<Product> productPage = productRepository.findAll(spec, paging);
        return productPage
                .stream()
                .map(ProductMapper::toDto)
                .peek(productDto -> productDto.setTotalPages(productPage.getTotalPages()))
                .toList();
    }

    @Transactional
    public Product create(ProductDto dto, User user) {
        final Product product = ProductMapper.toEntity(dto);
        product.setUser(user);

        saveOrUseExistingEmbedded(product);

        return productRepository.save(product);
    }

    private void saveOrUseExistingEmbedded(Product product) {
        final Coordinates coordinates = product.getCoordinates();
        if (coordinates != null) {
            Coordinates existingCoordinates = coordinatesRepository
                    .findByXAndY(coordinates.getX(), coordinates.getY())
                    .orElse(null);
            if (existingCoordinates != null) {
                product.setCoordinates(existingCoordinates);
            } else {
                coordinatesRepository.save(coordinates);
            }
        }

        Organization organization = product.getManufacturer();
        if (organization != null) {
            Organization existingOrganization = organizationRepository
                    .findByName(organization.getName())
                    .orElse(null);
            if (existingOrganization != null) {
                product.setManufacturer(existingOrganization);
            } else {
                final Address address = organization.getOfficialAddress();
                if (address != null) {
                    Address existingAddress = addressRepository
                            .findByZipCode(address.getZipCode())
                            .orElse(null);
                    if (existingAddress != null) {
                        organization.setOfficialAddress(existingAddress);
                    } else {
                        addressRepository.save(address);
                    }
                }
                if (organization.getId() == null) {
                    organizationRepository.save(organization);
                } else {
                    organization = organizationRepository.saveAndFlush(organization);
                    product.setManufacturer(organization);
                }
            }
        }


        final Person person = product.getOwner();
        if (person != null) {
            Person existingPerson = personRepository
                    .findByName(person.getName())
                    .orElse(null);
            if (existingPerson != null) {
                product.setOwner(existingPerson);
            } else {
                final Location location = person.getLocation();
                if (location != null) {
                    Location existingLocation = locationRepository
                            .findByXAndY(location.getX(), location.getY())
                            .orElse(null);
                    if (existingLocation != null) {
                        person.setLocation(existingLocation);
                    } else {
                        locationRepository.save(location);
                    }
                }
                personRepository.save(person);
            }
        }

        System.out.println("new product: " + product);
    }

//    public void checkUser(long id, User user) {
//        final Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found by this id"));
//        if (product.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
//            return;
//        }
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not user of this product");
//    }


    public Product update(ProductDto dto) {
        final Product product = ProductMapper.toEntity(dto);
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + product.getId()));

        if (!existingProduct.getCoordinates().equals(product.getCoordinates())) {
            Coordinates existingCoordinates = coordinatesRepository.findByXAndY(
                    product.getCoordinates().getX(),
                    product.getCoordinates().getY()
            ).orElse(null);

            if (existingCoordinates != null) {
                product.setCoordinates(existingCoordinates);
            } else {
                coordinatesRepository.delete(existingProduct.getCoordinates());
                coordinatesRepository.save(product.getCoordinates());
            }
        }

        product.setManufacturer(product.getManufacturer());

        product.setOwner(product.getOwner());

        System.out.println("Updated product: " + product);
        return productRepository.save(product);
    }


    public void removeByRating(Double rating, User user) {
        final List<Product> products = productRepository.findAllByUserAndRating(user, rating);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found");
        }
        productRepository.delete(products.get(0));
    }


    public Double getSumRating(User user) {
        final List<Product> products = productRepository.findAllByUser(user);
        return products
                .stream()
                .mapToDouble(Product::getRating)
                .sum();
    }


    public List<ProductDto> getAllProductsBySubstring(String substring, Pageable paging, ProductFilter productFilter) {
        final Specification<Product> spec = productSpecificationService.filterBy(productFilter);
        final Page<Product> productPage = productRepository.findAll(spec, paging);
        return productPage
                .stream()
                .filter(product -> product.getName().contains(substring))
                .map(ProductMapper::toDto)
                .peek(productDto -> productDto.setTotalPages(productPage.getTotalPages()))
                .toList();
    }

    @Transactional
    public void decreasePriceOnPercent(Integer percent, User user) {
        System.out.println("user " + user);
        final List<Product> allProducts = productRepository.findAll();
        System.out.println(allProducts);
        final List<Product> products = productRepository.findAllByUser(user);
        System.out.println(products);
        for (Product product : products) {
            final Long newPrice = Math.round(product.getPrice() * (1 - (double) percent / (double) 100));
            product.setPrice(newPrice);
            System.out.println("Decreased price on " + percent + "%: " + product);
            productRepository.save(product);
        }
    }





    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        List<Product> productsWithTheSameCoordinates = productRepository.findAllByCoordinates(product.getCoordinates());
        List<Product> productsWithTheSameManufacturer = productRepository.findAllByManufacturer(product.getManufacturer());
        productRepository.deleteAll(productsWithTheSameCoordinates);
        productRepository.deleteAll(productsWithTheSameManufacturer);
        coordinatesRepository.delete(product.getCoordinates());
        productRepository.deleteById(id);
    }
}
