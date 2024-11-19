package is.lab1.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<Organization> getOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PostMapping
    public ResponseEntity<Organization> create(@RequestBody OrganizationDto dto) {
        return new ResponseEntity<>(organizationService.create(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Organization> update(@RequestBody Organization organization) {
        return new ResponseEntity<>(organizationService.update(organization), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public HttpStatus deleteOrganization(@PathVariable("id") Long id) {
        organizationService.delete(id);
        return HttpStatus.OK;
    }
}