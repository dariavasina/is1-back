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
}
