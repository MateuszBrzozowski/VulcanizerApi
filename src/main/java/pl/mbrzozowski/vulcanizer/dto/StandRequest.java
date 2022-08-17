package pl.mbrzozowski.vulcanizer.dto;

import lombok.Data;

@Data
public class StandRequest {
    private String branchId;
    private String count;
    private String number;
}
