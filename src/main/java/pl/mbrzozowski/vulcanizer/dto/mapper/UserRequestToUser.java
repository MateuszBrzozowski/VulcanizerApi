package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserRequestToUser implements Function<UserRequest, User> {

    @Override
    public User apply(UserRequest userRequest) {
        User user = User.builder()
                .id(userRequest.getId())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .birthDate(userRequest.getBirthDate())
                .build();
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail().toLowerCase());
        }
        return user;
    }
}
