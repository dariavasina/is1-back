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

    public static LocationDto toDto(Location entity) {
        if (entity == null) {
            return null;
        }
        return LocationDto.builder()
                .id(entity.getId())
                .x(entity.getX())
                .y(entity.getY())
                .name(entity.getName())
                .build();
    }
}
