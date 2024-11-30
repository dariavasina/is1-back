package is.lab1.product;

import is.lab1.coordinates.Coordinates;
import is.lab1.organization.Organization;
import is.lab1.person.Person;
import is.lab1.user.User;
import is.lab1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;


    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }


//    public List<Product> getProducts() {
//        return productService.getProducts();
//    }
    @GetMapping
    ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                    @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                    @RequestParam(value = "id", required = false) Long id,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "coordinates", required = false) Coordinates coordinates,
                                                    @RequestParam(value = "createdAt", required = false) Long creationDateTimestampMs,
                                                    @RequestParam(value = "unitOfMeasure", required = false) UnitOfMeasure unitOfMeasure,
                                                    @RequestParam(value = "manufacturer", required = false) Organization manufacturer,
                                                    @RequestParam(value = "price", required = false) Integer price,
                                                    @RequestParam(value = "manufactureCost", required = false) Long manufactureCost,
                                                    @RequestParam(value = "rating", required = false) Double rating,
                                                    @RequestParam(value = "owner", required = false) Person owner,
                                                    @RequestParam(value = "login", required = false) String login,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "true") boolean ascending) {
        final ProductFilter productFilter = new ProductFilter(
                id,
                name,
                convertUnixTimestampToLocalDate(creationDateTimestampMs),
                coordinates,
                unitOfMeasure,
                manufacturer,
                price,
                manufactureCost,
                rating,
                owner,
                userService.getUserByLogin(login)
        );
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, limit, sort), productFilter));
    }

//    @PostMapping
//    public void registerNewProduct(@RequestBody Product product) {
//        productService.addNewProduct(product);
//    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDto dto, @RequestHeader(name = "Authorization") String token) {
        final User user = userService.getUserByToken(getToken(token));

        Product createdProduct = productService.create(dto, user);

        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto dto, @RequestHeader(name = "Authorization") String token) {
        System.out.println("ProductController.update");
        final User user = userService.getUserByToken(getToken(token));
//        productService.checkUser(dto.getId(), user);
        return ResponseEntity.ok(ProductMapper.toDto(productService.update(dto)));
        //return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }


    @DeleteMapping(path ="{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }

    private String getToken(String bearerToken) {
        return bearerToken.split(" ")[1];
    }


    @DeleteMapping("/rating/{rating}")
    public ResponseEntity<Void> removeByRating(@PathVariable("rating") Double rating, @RequestHeader(name = "Authorization") String token)
    {
        final User user = userService.getUserByToken(getToken(token));
        productService.removeByRating(rating, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rating")
    public ResponseEntity<Double> sumRating(@RequestHeader(name = "Authorization") String token) {
        final User user = userService.getUserByToken(getToken(token));
        return ResponseEntity.ok(productService.getSumRating(user));
    }

    @GetMapping("/substring/{substring}")
    public ResponseEntity<List<ProductDto>> getAllProductsBySubstring(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                      @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                                      @RequestParam(value = "id", required = false) Long id,
                                                                      @RequestParam(value = "name", required = false) String name,
                                                                      @RequestParam(value = "coordinates", required = false) Coordinates coordinates,
                                                                      @RequestParam(value = "createdAt", required = false) Long creationDateTimestampMs,
                                                                      @RequestParam(value = "unitOfMeasure", required = false) UnitOfMeasure unitOfMeasure,
                                                                      @RequestParam(value = "manufacturer", required = false) Organization manufacturer,
                                                                      @RequestParam(value = "price", required = false) Integer price,
                                                                      @RequestParam(value = "manufactureCost", required = false) Long manufactureCost,
                                                                      @RequestParam(value = "rating", required = false) Double rating,
                                                                      @RequestParam(value = "owner", required = false) Person owner,
                                                                      @RequestParam(value = "login", required = false) String login,
                                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                                      @RequestParam(defaultValue = "true") boolean ascending,
                                                                      @PathVariable("substring") String substring) {
        final ProductFilter productFilter = new ProductFilter(
                id,
                name,
                convertUnixTimestampToLocalDate(creationDateTimestampMs),
                coordinates,
                unitOfMeasure,
                manufacturer,
                price,
                manufactureCost,
                rating,
                owner,
                userService.getUserByLogin(login)
        );
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return ResponseEntity.ok(productService.getAllProductsBySubstring(substring, PageRequest.of(page, limit, sort), productFilter));
    }

    @PutMapping("/price:decrease/{percent}")
    public ResponseEntity<Void> decreasePriceOnPercent(@PathVariable("percent") @Min(0) @Max(100) Integer percent,
                                                       @RequestHeader(name = "Authorization") String token) {
        System.out.println("ProductController.decreasePriceOnPercent");
        final User user = userService.getUserByToken(getToken(token));
        productService.decreasePriceOnPercent(percent, user);
        return ResponseEntity.noContent().build();
    }

    private LocalDate convertUnixTimestampToLocalDate(Long unixTimestamp) {
        if (unixTimestamp == null) {
            return null;
        }
        return Instant
                .ofEpochMilli(unixTimestamp)
                .atOffset(ZoneOffset.UTC)
                .toLocalDate();
    }


}
