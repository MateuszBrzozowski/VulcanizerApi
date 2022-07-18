package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.enums.converter.GenderConverter;
import pl.mbrzozowski.vulcanizer.enums.converter.UserStatusAccountConverter;

import java.time.LocalDate;

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
    private String gender;
    private LocalDate birthDate;
    private String statusAccount;
    private AddressRequest address;
    private Long idAvatar;
    private Long idPhone;

    public UserRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setGender(int gender) {
        this.gender = GenderConverter.convert(gender);
    }

    public void setStatusAccount(int statusAccount) {
        this.statusAccount = UserStatusAccountConverter.convert(statusAccount);
    }

    public static CustomUserRequestBuilder builder() {
        return new CustomUserRequestBuilder();
    }

    public static class CustomUserRequestBuilder extends UserRequestBuilder {

        @Override
        public UserRequest build() {
            if (super.statusAccount == null) {
                super.statusAccount = UserStatusAccount.NOT_ACTIVATED.name();
            }
            return super.build();
        }
    }
}
