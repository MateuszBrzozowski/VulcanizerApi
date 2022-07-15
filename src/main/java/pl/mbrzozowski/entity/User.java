package pl.mbrzozowski.entity;

import lombok.Builder;
import lombok.Data;
import pl.mbrzozowski.enums.Gender;
import pl.mbrzozowski.enums.StatusUserAccount;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private final String email;
    private final String password;
    @Column(name = "first_name")
    private final String firsName;
    @Column(name = "last_name")
    private final String lastName;
    private Gender gender;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "create_time")
    private final LocalDateTime createAccountTime;
    @Column(name = "status")
    private final String statusAccount;
    private Long idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder()
    public User(final String email,
                final String password,
                final String firsName,
                final String lastName,
                final Gender gender,
                final LocalDate birthDate,
                final LocalDateTime createAccountTime,
                final StatusUserAccount statusAccount,
                final Long idAddress,
                final Long idAvatar,
                final Long idPhone) {

        this.email = email;
        this.password = password;
        this.firsName = firsName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = StatusUserAccount.NOT_ACTIVATED.name();
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
        this.firsName = firsName;
        this.lastName = lastName;
        this.statusAccount = StatusUserAccount.NOT_ACTIVATED.name();
        this.createAccountTime = LocalDateTime.now();
    }

    public User(final String email,
                final String password,
                final String firsName,
                final String lastName,
                final LocalDateTime createAccountTime,
                final StatusUserAccount statusAccount) {

        this.email = email;
        this.password = password;
        this.firsName = firsName;
        this.lastName = lastName;
        this.createAccountTime = Objects.requireNonNullElseGet(createAccountTime, LocalDateTime::now);
        this.statusAccount = Objects.requireNonNullElse(statusAccount, StatusUserAccount.NOT_ACTIVATED).name();
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
                super.statusAccount = StatusUserAccount.NOT_ACTIVATED;
            }
            return super.build();
        }
    }
}

