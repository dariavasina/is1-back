package is.lab1.product;

import is.lab1.coordinates.CoordinatesMapper;
import is.lab1.location.LocationMapper;
import is.lab1.organization.OrganizationMapper;
import is.lab1.person.Person;
import is.lab1.person.PersonDto;
import is.lab1.person.PersonMapper;
import is.lab1.user.UserMapper;

public class ProductMapper {
    public static Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .coordinates(CoordinatesMapper.toEntity(dto.getCoordinates()))
                .creationDate(dto.getCreationDate())
                .unitOfMeasure(dto.getUnitOfMeasure())
                .manufacturer(OrganizationMapper.toEntity(dto.getManufacturer()))
                .price(dto.getPrice())
                .manufactureCost(dto.getManufactureCost())
                .rating(dto.getRating())
                .partNumber(dto.getPartNumber())
                .owner(PersonMapper.toEntity(dto.getOwner()))
//                .user(UserMapper.toEntity(dto.getUser()))
                .build();
    }

    public static ProductDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .coordinates(CoordinatesMapper.toDto(entity.getCoordinates()))
                .creationDate(entity.getCreationDate())
                .unitOfMeasure(entity.getUnitOfMeasure())
                .manufacturer(OrganizationMapper.toDto(entity.getManufacturer()))
                .price(entity.getPrice())
                .manufactureCost(entity.getManufactureCost())
                .rating(entity.getRating())
                .partNumber(entity.getPartNumber())
                .owner(PersonMapper.toDto(entity.getOwner()))
                .user(UserMapper.toDto(entity.getUser()))
                .build();
    }
}

