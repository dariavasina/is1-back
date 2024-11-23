package is.lab1.product;

import is.lab1.coordinates.Coordinates;
import is.lab1.organization.Organization;
import is.lab1.person.Person;
import is.lab1.user.User;

import java.time.LocalDate;

public record ProductFilter(
        Long id,
        String name,
        LocalDate creationDate,
        Coordinates coordinates,
        UnitOfMeasure unitOfMeasure,
        Organization manufacturer,
        Integer price,
        Long manufactureCost,
        Double rating,
        Person owner,
        User user
) {
}