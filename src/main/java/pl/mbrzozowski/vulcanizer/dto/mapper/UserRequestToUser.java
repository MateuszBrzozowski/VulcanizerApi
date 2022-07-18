package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.service.StateService;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserRequestToUser implements Function<UserRequest, User> {
    private final StateService stateService;

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
                .idAddress(new AddressRequestToAddress(stateService).apply(userRequest.getAddress()))
                .idAvatar(userRequest.getIdAvatar())
                .idPhone(userRequest.getIdPhone())
                .build();
    }
}
