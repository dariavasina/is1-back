package is.lab1.organization;

import is.lab1.address.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization create(OrganizationDto dto) {
        Organization organization = Organization.builder()
                .id(dto.getId())
                .name(dto.getName())
                .officialAddress(AddressMapper.toEntity(dto.getOfficialAddress()))
                .annualTurnover(dto.getAnnualTurnover())
                .employeesCount(dto.getEmployeesCount())
                .rating(dto.getRating())
                .type(dto.getType())
                .build();
        return organizationRepository.save(organization);
    }

    public Organization update(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void delete(Long id) {
        boolean exists = organizationRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Organization with id " + id + " does not exist");
        }
        organizationRepository.deleteById(id);
    }
}