package is.lab1.organization;


import is.lab1.address.Address;
import is.lab1.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrganizationDto {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private AddressDto officialAddress; //Поле может быть null
    private Integer annualTurnover; //Значение поля должно быть больше 0
    private Integer employeesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer rating; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
}
