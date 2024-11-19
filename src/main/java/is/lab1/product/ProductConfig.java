//package is.lab1.product;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class ProductConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(ProductRepository repository) {
//        return args-> {
//            Product A = new Product(
//                    "A", 10000L
//            );
//
//            Product B = new Product(
//                    "B", 500L
//            );
//            repository.saveAll(
//                    List.of(A, B)
//            );
//        };
//
//    }
//}
