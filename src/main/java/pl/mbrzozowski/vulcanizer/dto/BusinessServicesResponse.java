package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BusinessServicesResponse {
    private Long id;
    private String nameOne;
    private String nameTwo;
    private LocalTime executionTime;
    private double price;
}
