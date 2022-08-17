package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stand")
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "company_branch_id", nullable = false)
    private CompanyBranch companyBranch;

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
