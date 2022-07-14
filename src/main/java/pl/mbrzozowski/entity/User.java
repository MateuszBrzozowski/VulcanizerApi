package pl.mbrzozowski.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @Column(name = "first_name")
    private String firsName;
    @Column(name = "last_name")
    private String lastName;
    private Gender gender;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "create_time")
    private LocalDateTime createAccountTime;
    private Long idAddress;
    private Long idAvatar;
    private Long idPhone;

    @Builder
    public User(String username, String email, String firsName, String lastName, Gender gender, LocalDate birthDate, Long idAddress, Long idAvatar, Long idPhone) {
        this.username = username;
        this.email = email;
        this.firsName = firsName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.idAddress = idAddress;
        this.idAvatar = idAvatar;
        this.idPhone = idPhone;
    }

    public static class UserBuilder {
        //
    }
}
