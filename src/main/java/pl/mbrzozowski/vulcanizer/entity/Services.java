package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;
import pl.mbrzozowski.vulcanizer.enums.WheelType;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "Decimal(6,2)")
    private Double price;
    private String name;
    private LocalTime time;
    @Enumerated(EnumType.ORDINAL)
    private TypeOfServices typeOfServices;
    @Enumerated(EnumType.ORDINAL)
    private WheelType wheelType;
    private Integer sizeFrom;
    private Integer sizeTo;
}
