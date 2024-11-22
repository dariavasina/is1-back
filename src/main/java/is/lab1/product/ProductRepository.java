package is.lab1.product;

import is.lab1.user.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    List<Product> findAllByUserAndRating(User user, Double rating);

    List<Product> findAllByUser(User user);

    @Nonnull
    Page<Product> findAll(Specification<Product> spec, @Nonnull Pageable pageable);
}
