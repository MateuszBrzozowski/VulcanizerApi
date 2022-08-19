package pl.mbrzozowski.vulcanizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stand")
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_branch_id", nullable = false)
    private CompanyBranch companyBranch;
    @JsonIgnore
    @OneToMany(mappedBy = "stand", cascade = CascadeType.ALL)
    private List<CustomOpeningHours> customOpeningHours;

    @Override
    public String toString() {
        return "Stand{" +
                "id=" + id +
                ", number=" + number +
                ", companyBranchID=" + companyBranch.getId() +
                ", companyBranchName=" + companyBranch.getName() +
                '}';
    }
}
