package is.lab1.location;

public class LocationMapper {
    public static Location toEntity(LocationDto dto) {
        if (dto == null) {
            return null;
        }
        return Location.builder()
                .id(dto.getId())
                .x(dto.getX())
                .y(dto.getY())
                .name(dto.getName())
                .build();
    }
}
