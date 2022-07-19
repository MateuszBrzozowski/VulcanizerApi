package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;

import java.util.function.Function;

public class UserRegisterBodyToUserRequest implements Function<UserRegisterBody, UserRequest> {
    @Override
    public UserRequest apply(UserRegisterBody userRegisterBody) {
        return UserRequest.builder()
                .email(userRegisterBody.getEmail())
                .password(userRegisterBody.getPassword())
                .firstName(userRegisterBody.getFirstName())
                .lastName(userRegisterBody.getLastName())
                .build();
    }
}
