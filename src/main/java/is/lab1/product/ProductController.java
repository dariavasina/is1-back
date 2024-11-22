package is.lab1.product;

import is.lab1.user.User;
import is.lab1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
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
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }


    @DeleteMapping(path ="{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }

    private String getToken(String bearerToken) {
        return bearerToken.split(" ")[1];
    }
}
