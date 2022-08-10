package pl.mbrzozowski.vulcanizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.BusinessRole;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User userId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", nullable = false)
    private Company businessId;
    @Enumerated(EnumType.STRING)
    private BusinessRole role;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", userId=" + userId.getId() +
                ", businessId=" + businessId.getId() +
                ", role=" + role.name() +
                '}';
    }
}
