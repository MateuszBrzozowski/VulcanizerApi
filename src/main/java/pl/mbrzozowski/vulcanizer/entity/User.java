package pl.mbrzozowski.vulcanizer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;
    private Long idAvatar;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phone")
    private Phone phone;

    @Builder
    public User(final Long id,
                final String email,
                final String password,
                final String firstName,
                final String lastName,
                final String gender,
                final LocalDate birthDate,
                final LocalDateTime createAccountTime,
                final String statusAccount,
                final Address idAddress,
                final Long idAvatar,
                final Phone phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createAccountTime = createAccountTime;
        this.statusAccount = statusAccount;
        this.address = idAddress;
        this.idAvatar = idAvatar;
        this.phone = phone;
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

    public void setCreateAccountTime(LocalDateTime createAccountTime) {
        this.createAccountTime = createAccountTime;
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
                super.statusAccount = UserStatusAccount.NOT_ACTIVATED.name();
            }
            return super.build();
        }
    }
}

