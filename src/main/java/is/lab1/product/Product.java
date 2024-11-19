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

    @OneToOne(cascade = CascadeType.ALL)
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

//    @JoinColumn
//    @ManyToOne
//    private User user;
//    //todo
//
//    public Product(String name, Coordinates coordinates, Date creationDate,
//                   UnitOfMeasure unitOfMeasure, Organization manufacturer,
//                   Long price, Double manufactureCost, Integer rating, String partNumber, Person owner) {
//        this.name = name;
//        this.coordinates = coordinates;
//        this.creationDate = creationDate;
//        this.unitOfMeasure = unitOfMeasure;
//        this.manufacturer = manufacturer;
//        this.price = price;
//        this.manufactureCost = manufactureCost;
//        this.rating = rating;
//        this.partNumber = partNumber;
//        this.owner = owner;
//    }
}
