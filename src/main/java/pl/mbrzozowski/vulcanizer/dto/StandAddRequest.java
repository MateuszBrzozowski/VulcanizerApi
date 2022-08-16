package pl.mbrzozowski.vulcanizer.dto;

import lombok.Data;

@Data
public class StandAddRequest {
    private Long branchId;
    private String count;
}
