package is.lab1.product;

import is.lab1.coordinates.Coordinates;
import is.lab1.coordinates.CoordinatesDto;
import is.lab1.organization.Organization;
import is.lab1.organization.OrganizationDto;
import is.lab1.person.Person;
import is.lab1.person.PersonDto;
import is.lab1.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private CoordinatesDto coordinates;
    private java.util.Date creationDate;
    private UnitOfMeasure unitOfMeasure;
    private OrganizationDto manufacturer;
    private Long price;
    private Double manufactureCost;
    private Integer rating;
    private String partNumber;
    private PersonDto owner;
//    private UserDto user;
}
