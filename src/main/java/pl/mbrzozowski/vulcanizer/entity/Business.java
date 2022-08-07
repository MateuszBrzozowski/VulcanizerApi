package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;

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
    private String name;
    private String displayName;
    private String nip;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    private String description;
    private String status;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_photo")
    private Photo photo;

    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "business_phone",
            joinColumns = @JoinColumn(name = "id_business"),
            inverseJoinColumns = @JoinColumn(name = "id_phone")
    )
    private Set<Phone> phones;

    public void deletePhones(){
        phones.clear();
    }


    public void setStatus(BusinessStatus status) {
        this.status = status.name();
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nip='" + nip + '\'' +
                ", createdDate=" + createdDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", address=" + address +
                ", photo=" + photo +
                ", employees=" + employees +
                ", phones=" + phones +
                '}';
    }
}
