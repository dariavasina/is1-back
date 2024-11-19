package is.lab1.coordinates;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CoordinatesDto {
    private Long id;
    private Long x;
    private Double y;
}
