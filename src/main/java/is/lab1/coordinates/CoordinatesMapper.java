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
}
