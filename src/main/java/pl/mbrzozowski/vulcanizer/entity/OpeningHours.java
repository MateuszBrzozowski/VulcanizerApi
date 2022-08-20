package pl.mbrzozowski.vulcanizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "opening_hours")
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_branch_id")
    private CompanyBranch companyBranch;

    @Override
    public String toString() {
        return "OpeningHours{" +
                "id=" + id +
                ", day=" + day +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", companyBranch=" + companyBranch.getId() +
                '}';
    }
}
