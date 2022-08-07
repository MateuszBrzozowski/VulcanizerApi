package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.converter.Converter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String birthDate;
    private AddressRequest address;
    private String avatar;
    private String phone;

    public UserRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = Converter.convertStringToGender(gender);
    }

}
