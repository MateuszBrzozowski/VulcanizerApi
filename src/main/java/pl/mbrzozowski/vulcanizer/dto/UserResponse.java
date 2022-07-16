package pl.mbrzozowski.vulcanizer.dto;

import lombok.Builder;
import lombok.Data;
import pl.mbrzozowski.vulcanizer.entity.converter.UserStatusAccountConverter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDateTime createAccountTime;
    private String statusAccount;
    private Long idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder
    public UserResponse(Long id,
                        String email,
                        String password,
                        String firstName,
                        String lastName,
                        Gender gender,
                        LocalDate birthDate,
                        LocalDateTime createAccountTime,
                        UserStatusAccount statusAccount,
                        Long idAddress,
                        Long idAvatar,
                        Long idPhone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = statusAccount.name();
        this.idAddress = idAddress;
        this.idAvatar = idAvatar;
        this.idPhone = idPhone;
    }

    public UserStatusAccount getStatusAccount() {
        return UserStatusAccountConverter.convert(this.statusAccount);
    }

    public void setStatusAccount(UserStatusAccount statusAccount) {
        this.statusAccount = UserStatusAccountConverter.convert(statusAccount);
    }
}
