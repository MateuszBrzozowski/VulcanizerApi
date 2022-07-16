package pl.mbrzozowski.vulcanizer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.converter.GenderConverter;
import pl.mbrzozowski.vulcanizer.entity.converter.UserStatusAccountConverter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String gender;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "create_time")
    private LocalDateTime createAccountTime;
    @Column(name = "status")
    private String statusAccount;
    private Long idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder
    public User(final Long id,
                final String email,
                final String password,
                final String firstName,
                final String lastName,
                final Gender gender,
                final LocalDate birthDate,
                final LocalDateTime createAccountTime,
                final UserStatusAccount statusAccount,
                final Long idAddress,
                final Long idAvatar,
                final Long idPhone) {
        this.email = email;
        this.password = password;
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

    public User(final String email,
                final String password,
                final String firsName,
                final String lastName) {

        this.email = email;
        this.password = password;
        this.firstName = firsName;
        this.lastName = lastName;
        this.statusAccount = UserStatusAccount.NOT_ACTIVATED.name();
        this.createAccountTime = LocalDateTime.now();
    }

    public Gender getGender() {
        return GenderConverter.convert(this.gender);
    }

    public void setGender(Gender gender) {
        this.gender = GenderConverter.convert(gender);
    }

    public void setStatusAccount(UserStatusAccount statusAccount) {
        this.statusAccount = UserStatusAccountConverter.convert(statusAccount);
    }

    public UserStatusAccount getStatusAccount() {
        return UserStatusAccountConverter.convert(this.statusAccount);
    }

    public static UserBuilder builder() {
        return new CustomUserBuilder();
    }

    private static class CustomUserBuilder extends UserBuilder {

        @Override
        public User build() {
            if (super.createAccountTime == null) {
                super.createAccountTime = LocalDateTime.now();
            }
            if (super.statusAccount == null) {
                super.statusAccount = UserStatusAccount.NOT_ACTIVATED;
            }
            return super.build();
        }
    }
}

