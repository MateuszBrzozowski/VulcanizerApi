package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessServiceRequest {
    private Long id;
    private Long business;
    private String nameOne;
    private String nameTwo;
    private LocalTime executionTime;
    private double price = 0;
}
