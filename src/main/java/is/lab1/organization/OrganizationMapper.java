package is.lab1.organization;

import is.lab1.address.AddressMapper;
import is.lab1.coordinates.Coordinates;
import is.lab1.coordinates.CoordinatesDto;

public class OrganizationMapper {
    public static Organization toEntity(OrganizationDto dto) {
        if (dto == null) {
            return null;
        }
        return Organization.builder()
                .id(dto.getId())
                .name(dto.getName())
                .officialAddress(AddressMapper.toEntity(dto.getOfficialAddress()))
                .annualTurnover(dto.getAnnualTurnover())
                .employeesCount(dto.getEmployeesCount())
                .rating(dto.getRating())
                .type(dto.getType())
                .build();
    }

    public static OrganizationDto toDto(Organization entity) {
        if (entity == null) {
            return null;
        }
        return OrganizationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .officialAddress(AddressMapper.toDto(entity.getOfficialAddress()))
                .annualTurnover(entity.getAnnualTurnover())
                .employeesCount(entity.getEmployeesCount())
                .rating(entity.getRating())
                .type(entity.getType())
                .build();
    }

}

