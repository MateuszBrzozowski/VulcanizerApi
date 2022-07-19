package pl.mbrzozowski.vulcanizer.dto;

import lombok.Builder;
import lombok.Data;
import pl.mbrzozowski.vulcanizer.entity.Photo;

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
    private AddressResponse address;
    private String avatar;
    private String phone;

    @Builder
    public UserResponse(final Long id,
                        final String email,
                        final String firstName,
                        final String lastName,
                        final String gender,
                        final LocalDate birthDate,
                        final LocalDateTime createAccountTime,
                        final String statusAccount,
                        final AddressResponse address,
                        final String avatar,
                        final String idPhone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = statusAccount;
        this.address = address;
        this.avatar = avatar;
        this.phone = idPhone;
    }


}
