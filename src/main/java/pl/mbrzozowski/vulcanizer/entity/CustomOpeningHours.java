package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "custom_opening_hours")
public class CustomOpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    @ManyToOne
    @JoinColumn(name = "id_company_branch", nullable = false)
    private CompanyBranch companyBranch;
    @ManyToOne
    @JoinColumn(name = "id_stand")
    private Stand stand;
}
