package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginBody {
    private String email;
    private String password;
}
