package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;

public class UserToUserResponse implements Converter<User, UserResponse> {
    @Override
    public UserResponse convert(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .createAccountTime(user.getCreateAccountTime())
                .address(new AddressToAddressResponse().apply(user.getAddress()))
                .build();
        if (user.getPhone() != null) {
            userResponse.setPhone(user.getPhone().getNumber());
        }
        if (user.getAvatar() != null) {
            userResponse.setAvatar(user.getAvatar().getUrl());
        }
        if (user.getGender() != null) {
            userResponse.setGender(user.getGender().toString());
        }
        return userResponse;
    }
}
