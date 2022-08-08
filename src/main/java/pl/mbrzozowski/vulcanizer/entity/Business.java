package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "display_name")
    private String displayName;
    @Column(length = 10, nullable = false)
    private String nip;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @Column(length = 1000)
    private String description;
    @OneToOne
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_photo")
    private Photo photo;
    @OneToMany(mappedBy = "businessId",fetch = FetchType.EAGER)
    private List<Employee> employees;
    @ManyToMany
    @JoinTable(
            name = "business_phone",
            joinColumns = @JoinColumn(name = "id_business"),
            inverseJoinColumns = @JoinColumn(name = "id_phone")
    )
    private Set<Phone> phones;

    private boolean isActive;
    private boolean isLocked;
    private boolean isClosed;

    public void deletePhones() {
        phones.clear();
    }
}
