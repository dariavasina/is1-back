package is.lab1.address;

public class AddressMapper {
    public static Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        return Address.builder()
                .id(dto.getId())
                .zipCode(dto.getZipCode())
                .build();
    }

    public static AddressDto toDto(Address entity) {
        if (entity == null) {
            return null;
        }
        return AddressDto.builder()
                .id(entity.getId())
                .zipCode(entity.getZipCode())
                .build();
    }
}
