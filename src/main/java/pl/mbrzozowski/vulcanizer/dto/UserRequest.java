package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.enums.converter.GenderConverter;
import pl.mbrzozowski.vulcanizer.enums.converter.UserStatusAccountConverter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

import java.time.LocalDate;

@Data
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
    private Address idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder
    public UserRequest(final Long id,
                       final String email,
                       final String password,
                       final String firstName,
                       final String lastName,
                       final Gender gender,
                       final LocalDate birthDate,
                       final UserStatusAccount statusAccount,
                       final Address idAddress,
                       final Long idAvatar,
                       final Long idPhone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        if (gender != null) {
            this.gender = gender.name();
        }
        this.birthDate = birthDate;
        this.statusAccount = statusAccount.name();
        this.idAddress = idAddress;
        this.idAvatar = idAvatar;
        this.idPhone = idPhone;
    }

    public UserRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Gender getGender() {
        return GenderConverter.convert(this.gender);
    }

    public void setGender(Gender gender) {
        this.gender = GenderConverter.convert(gender);
    }

    public void setGender(String gender) {
        if (gender != null) {
            setGender(GenderConverter.convert(gender));
        }
    }

    public void setGender(int gender) {
        setGender(GenderConverter.convert(gender));
    }

    public UserStatusAccount getStatusAccount() {
        return UserStatusAccountConverter.convert(this.statusAccount);
    }

    public void setStatusAccount(UserStatusAccount statusAccount) {
        this.statusAccount = UserStatusAccountConverter.convert(statusAccount);
    }

    public void setStatusAccount(String statusAccount) {
        if (statusAccount !=null) {
            setStatusAccount(UserStatusAccountConverter.convert(statusAccount));
        }
    }

    public void setStatusAccount(int statusAccount) {
        setStatusAccount(UserStatusAccountConverter.convert(statusAccount));
    }

    public static CustomUserRequestBuilder builder() {
        return new CustomUserRequestBuilder();
    }

    public static class CustomUserRequestBuilder extends UserRequestBuilder {

        @Override
        public UserRequestBuilder gender(Gender gender) {
            if (gender == null) {
                this.gender(null);
            }
            return this;
        }

        @Override
        public UserRequest build() {
            if (super.statusAccount == null) {
                super.statusAccount = UserStatusAccount.NOT_ACTIVATED;
            }
            return super.build();
        }
    }
}
