package pl.mbrzozowski.vulcanizer.dto;

import lombok.Builder;
import lombok.Data;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.enums.converter.GenderConverter;
import pl.mbrzozowski.vulcanizer.enums.converter.UserStatusAccountConverter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private LocalDateTime createAccountTime;
    private String statusAccount;
    private Address idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder
    public UserResponse(final Long id,
                        final String email,
                        final String firstName,
                        final String lastName,
                        final Gender gender,
                        final LocalDate birthDate,
                        final LocalDateTime createAccountTime,
                        final UserStatusAccount statusAccount,
                        final Address idAddress,
                        final Long idAvatar,
                        final Long idPhone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        if (gender != null) {
            this.gender = gender.name();
        }
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = statusAccount.name();
        this.idAddress = idAddress;
        this.idAvatar = idAvatar;
        this.idPhone = idPhone;
    }

    public Gender getGender() {
        return GenderConverter.convert(this.gender);
    }

    public void setGender(Gender gender) {
        this.gender = GenderConverter.convert(gender);
    }

    public UserStatusAccount getStatusAccount() {
        return UserStatusAccountConverter.convert(this.statusAccount);
    }

    public void setStatusAccount(UserStatusAccount statusAccount) {
        this.statusAccount = UserStatusAccountConverter.convert(statusAccount);
    }
}
