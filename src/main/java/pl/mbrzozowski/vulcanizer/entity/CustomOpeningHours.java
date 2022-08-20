package pl.mbrzozowski.vulcanizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_branch_id", nullable = false)
    private CompanyBranch companyBranch;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stand_id")
    private Stand stand;
}
