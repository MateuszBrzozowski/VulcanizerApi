package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.service.PhoneService;
import pl.mbrzozowski.vulcanizer.service.StateService;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserRequestToUser implements Function<UserRequest, User> {
    private final StateService stateService;
    private final PhoneService phoneService;

    @Override
    public User apply(UserRequest userRequest) {
        User user = User.builder()
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
                .build();
        if (userRequest.getPhone() != null) {
            try {
                Phone phone = phoneService.findByNumber(userRequest.getPhone());
                user.setPhone(phone);
            } catch (NoSuchElementException exception) {
                user.setPhone(new Phone(userRequest.getPhone()));
            }
        }

        return user;
    }
}
