package is.lab1;

import is.lab1.organization.OrganizationType;
import is.lab1.product.Color;
import is.lab1.product.UnitOfMeasure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ConstantsController{
    @GetMapping("/colors")
    public ResponseEntity<List<Color>> getAllColors() {
        return ResponseEntity.ok(Arrays.stream(Color.values()).toList());
    }

    @GetMapping("/organizationTypes")
    public ResponseEntity<List<OrganizationType>> getAllCountries() {
        return ResponseEntity.ok(Arrays.stream(OrganizationType.values()).toList());
    }

    @GetMapping("/measures")
    public ResponseEntity<List<UnitOfMeasure>> getAllMeasures() {
        return ResponseEntity.ok(Arrays.stream(UnitOfMeasure.values()).toList());
    }
}
