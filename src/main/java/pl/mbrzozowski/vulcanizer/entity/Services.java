package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.TypOfServices;
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
    private double price;
    private LocalTime time;
    @Enumerated(EnumType.ORDINAL)
    private TypOfServices typOfServices;
    @Enumerated(EnumType.ORDINAL)
    private WheelType wheelType;
    private int sizeFrom;
    private int sizeTo;
}
