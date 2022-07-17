package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.function.Function;

public class UserRequestToUser implements Function<UserRequest, User> {
    @Override
    public User apply(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.getId())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .birthDate(userRequest.getBirthDate())
                .statusAccount(userRequest.getStatusAccount())
                .idAddress(userRequest.getIdAddress())
                .idAvatar(userRequest.getIdAvatar())
                .idPhone(userRequest.getIdPhone())
                .build();
    }
}
