package is.lab1.product;

import is.lab1.coordinates.CoordinatesMapper;
import is.lab1.organization.OrganizationMapper;
import is.lab1.person.PersonMapper;
import is.lab1.user.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void create(Product product) {
        productRepository.save(product);
    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product create(ProductDto dto) {
        Product product = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .coordinates(CoordinatesMapper.toEntity(dto.getCoordinates()))
                .creationDate(dto.getCreationDate())
                .unitOfMeasure(dto.getUnitOfMeasure())
                .manufacturer(OrganizationMapper.toEntity(dto.getManufacturer()))
                .price(dto.getPrice())
                .manufactureCost(dto.getManufactureCost())
                .rating(dto.getRating())
                .partNumber(dto.getPartNumber())
                .owner(PersonMapper.toEntity(dto.getOwner()))
//                .user(UserMapper.toEntity(dto.getUser()))
                .build();
        return productRepository.save(product);
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
