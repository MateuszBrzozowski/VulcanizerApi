package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.function.Function;

public class UserToUserResponse implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
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