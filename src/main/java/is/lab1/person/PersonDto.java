package is.lab1.person;

import is.lab1.location.Location;
import is.lab1.location.LocationDto;
import is.lab1.product.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private Color eyeColor;
    private Color hairColor;
    private LocationDto location;
    private Long weight;
}
