package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateResponse {
    private Long id;
    private String name;
}
