package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany
    private List<Employee> employees;



    public void setStatus(BusinessStatus status) {
        this.status = status.name();
    }
}
