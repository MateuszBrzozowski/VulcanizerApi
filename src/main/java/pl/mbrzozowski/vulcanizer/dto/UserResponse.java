package pl.mbrzozowski.vulcanizer.dto;

import lombok.Builder;
import lombok.Data;
import pl.mbrzozowski.vulcanizer.entity.Phone;

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
    private Long idAvatar;
    private Phone idPhone;

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
                        final Long idAvatar,
                        final Phone idPhone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = statusAccount;
        this.address = address;
        this.idAvatar = idAvatar;
        this.idPhone = idPhone;
    }


}
