package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.TypOfServices;
import pl.mbrzozowski.vulcanizer.enums.WheelType;

import javax.persistence.Entity;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "services")
public class Services {
    private Long id;
    private double price;
    private LocalTime time;
    private TypOfServices typOfServices;
    private WheelType wheelType;
    private int sizeFrom;
    private int sizeTo;
}
