package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Email
    private String email;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "create_time")
    private LocalDateTime createAccountTime;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatusAccount statusAccount;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_avatar")
    private Photo avatar;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phone")
    private Phone phone;

    @OneToMany(mappedBy = "userId")
    private List<Employee> employees;
    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites;
    @OneToMany(mappedBy = "user")
    private List<Visit> visits;


    private String[] roles;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

//    @Builder
//    public User(final Long id,
//                final String email,
//                final String password,
//                final String firstName,
//                final String lastName,
//                final Gender gender,
//                final LocalDate birthDate,
//                final LocalDateTime createAccountTime,
//                final UserStatusAccount statusAccount,
//                final Address idAddress,
//                final Photo avatar,
//                final Phone phone) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.gender = gender;
//        this.birthDate = birthDate;
//        this.createAccountTime = LocalDateTime.now();
//        this.statusAccount = statusAccount;
//        this.address = idAddress;
//        this.avatar = avatar;
//        this.phone = phone;
//    }

    public User(final String email,
                final String password,
                final String firsName,
                final String lastName) {

        this.email = email;
        this.password = password;
        this.firstName = firsName;
        this.lastName = lastName;
        this.statusAccount = UserStatusAccount.NOT_ACTIVATED;
        this.createAccountTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", createAccountTime=" + createAccountTime +
                ", statusAccount=" + statusAccount +
                ", address=" + address +
                ", avatar=" + avatar +
                ", phone=" + phone +
                '}';
    }
}

