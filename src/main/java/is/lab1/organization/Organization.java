package is.lab1.organization;


import is.lab1.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private Address officialAddress;

    @Min(1)
    @Column
    private Integer annualTurnover;

    @Column(nullable = false)
    @Min(1)
    private Integer employeesCount;

    @Min(1)
    @Column
    private Integer rating;

    @Column(nullable = false)
    private OrganizationType type;
}
