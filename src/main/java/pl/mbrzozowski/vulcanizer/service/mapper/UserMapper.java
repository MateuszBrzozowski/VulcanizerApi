package pl.mbrzozowski.vulcanizer.service.mapper;

import org.springframework.stereotype.Component;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;

@Component
public class UserMapper {
    public static User map(UserRequest requestUser) {
        return User.builder()
                .email(requestUser.getEmail())
                .password(requestUser.getPassword())
                .firsName(requestUser.getFirstName())
                .lastName(requestUser.getLastName())
                .gender(requestUser.getGender())
                .birthDate(requestUser.getBirthDate())
                .build();
    }

    public static UserResponse map(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .createAccountTime(user.getCreateAccountTime())
                .statusAccount(user.getStatusAccount())
                .idAddress(user.getIdAddress())
                .idAvatar(user.getIdAvatar())
                .idPhone(user.getIdPhone())
                .build();
    }

}
