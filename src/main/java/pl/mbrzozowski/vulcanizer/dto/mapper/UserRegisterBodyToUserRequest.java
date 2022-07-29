package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;

public class UserRegisterBodyToUserRequest implements Converter<UserRegisterBody, UserRequest> {
    @Override
    public UserRequest convert(UserRegisterBody source) {
        return UserRequest.builder()
                .email(source.getEmail())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
