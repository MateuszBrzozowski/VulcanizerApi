package pl.mbrzozowski.vulcanizer.service.mapper;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;

@Component
@Data
public class UserMapper {
    public static User getUser(UserRequest requestUser) {
        return User.builder()
                .id(requestUser.getId())
                .email(requestUser.getEmail())
                .password(requestUser.getPassword())
                .firstName(requestUser.getFirstName())
                .lastName(requestUser.getLastName())
                .gender(requestUser.getGender())
                .birthDate(requestUser.getBirthDate())
                .statusAccount(requestUser.getStatusAccount())
                .idAddress(requestUser.getIdAddress())
                .idAvatar(requestUser.getIdAvatar())
                .idPhone(requestUser.getIdPhone())
                .build();
    }

    public static UserResponse getUser(User user) {
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

    public static User getUser(UserResponse userResponse) {
        return User.builder()
                .id(userResponse.getId())
                .email(userResponse.getEmail())
                .password(userResponse.getPassword())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .gender(userResponse.getGender())
                .birthDate(userResponse.getBirthDate())
                .statusAccount(userResponse.getStatusAccount())
                .idAddress(userResponse.getIdAddress())
                .idAvatar(userResponse.getIdAvatar())
                .idPhone(userResponse.getIdPhone())
                .build();
    }
}
