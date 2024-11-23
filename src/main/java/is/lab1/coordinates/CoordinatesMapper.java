package is.lab1.coordinates;


public class CoordinatesMapper {
    public static Coordinates toEntity(CoordinatesDto dto) {
        if (dto == null) {
            return null;
        }
        return Coordinates.builder()
                .x(dto.getX())
                .y(dto.getY())
                .build();
    }

    public static CoordinatesDto toDto(Coordinates entity) {
        if (entity == null) {
            return null;
        }
        return CoordinatesDto.builder()
                .x(entity.getX())
                .y(entity.getY())
                .build();
    }
}
