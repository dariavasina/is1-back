package is.lab1.location;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class LocationDto {
    private Long id;
    private Float x;
    private Integer y;
    private String name;
}
