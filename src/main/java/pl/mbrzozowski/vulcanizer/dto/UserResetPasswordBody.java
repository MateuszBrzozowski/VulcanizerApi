package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPasswordBody {
    private String token;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
