package is.lab1.product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
    public ResponseEntity<Product> create(@RequestBody ProductDto dto) {
        return new ResponseEntity<>(productService.create(dto), HttpStatus.OK);
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
}
