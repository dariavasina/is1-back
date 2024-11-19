package is.lab1.person;


import is.lab1.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import is.lab1.product.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color eyeColor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color hairColor;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @Column
    @Min(1)
    private Long weight;
}
