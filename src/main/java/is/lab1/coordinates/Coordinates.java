package is.lab1.coordinates;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coordinates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"x", "y"})
})
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long x;


    @Column(nullable = false)
    @Min(value = -511)
    private Double y;
}
