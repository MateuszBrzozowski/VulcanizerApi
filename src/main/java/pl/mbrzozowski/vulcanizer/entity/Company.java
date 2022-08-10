package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String nip;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @OneToOne
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;
    @OneToMany(mappedBy = "businessId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToOne
    @JoinColumn(name = "phone", nullable = false)
    private Phone phone;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyBranch> companyBranch;

    private boolean isActive;
    private boolean isLocked;
    private boolean isClosed;
}
