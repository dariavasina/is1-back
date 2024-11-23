package is.lab1.product;

import is.lab1.coordinates.Coordinates;
import is.lab1.organization.Organization;
import is.lab1.person.Person;
import is.lab1.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Coordinates coordinates;

    @Column(nullable = false)
    @CreationTimestamp
    private java.util.Date creationDate;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private Organization manufacturer;

    @Column(nullable = false)
    @Min(1)
    private Long price;

    @Column
    private Double manufactureCost;

    @Column(nullable = false)
    @Min(1)
    private Integer rating;

    @Column
    private String partNumber;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;

    @JoinColumn
    @ManyToOne
    private User user;


}
