package pl.mbrzozowski.vulcanizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "company_branch")
public class CompanyBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToOne
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @OneToOne
    @JoinColumn(name = "phone", nullable = false)
    private Phone phone;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_photo")
    private Photo photo;
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private Company company;
    @JsonIgnore
    @OneToMany(mappedBy = "companyBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stand> stands;
    @JsonIgnore
    @OneToMany(mappedBy = "companyBranch", cascade = CascadeType.ALL)
    private List<OpeningHours> openingHours;
    private boolean isActive;
    private boolean isLocked;
    private boolean isClosed;
}
