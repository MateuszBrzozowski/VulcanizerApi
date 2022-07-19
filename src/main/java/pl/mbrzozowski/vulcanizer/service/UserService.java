package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressResponseToAddress;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceLayer<UserRequest, UserResponse, User> {
    private final UserRepository userRepository;
    private final StateService stateService;
    private final AddressService addressService;
    private final PhoneService phoneService;

    @Override
    public User save(UserRequest userRequest) {
        User user = new UserRequestToUser(stateService, phoneService).apply(userRequest);
        ValidationUser validationUser = new ValidationUser(userRepository);
        user.setStatusAccount(UserStatusAccount.NOT_ACTIVATED.name());
        user.setCreateAccountTime(LocalDateTime.now());
        validationUser.accept(user);
        userRepository.save(user);
        return user;
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        User isUser = findById(userRequest.getId());
        User userEdit = new UserRequestToUser(stateService, phoneService).apply(userRequest);
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.accept(userEdit);
        //TODO REFACTOR CODE BELOW - Extract Method
        //Address
        if (isUser.getAddress() == null && userEdit.getAddress() != null) { //DB no address, add address from req
            Address address = addressService.save(userRequest.getAddress());
            userEdit.setAddress(address);
        } else if (isUser.getAddress() != null && userEdit.getAddress() != null) { // DB has an address, edit address from req
            AddressRequest addressRequest = userRequest.getAddress();
            addressRequest.setId(isUser.getAddress().getId());
            AddressResponse addressResponse = addressService.update(addressRequest);
            userEdit.setAddress(new AddressResponseToAddress(stateService).apply(addressResponse));
        } else if (isUser.getAddress() != null && userEdit.getAddress() == null) { // DB has an address, delete address from DB.
            Long id = isUser.getAddress().getId();
            deleteAddressFromUser(isUser.getId());
            addressService.deleteById(id);
        }

        //Phone
        if (isUser.getPhone() == null && userEdit.getPhone() != null) { //DB no phone, add phone from req
            Phone phone = phoneService.save(userEdit.getPhone());
            userEdit.setPhone(phone);
        } else if (isUser.getPhone() != null && userEdit.getPhone() != null) { //DB has a phone, edit phone from req
            Long id = isUser.getPhone().getId();
            userEdit.getPhone().setId(id);
            Phone phone = phoneService.update(userEdit.getPhone());
            userEdit.setPhone(phone);
        } else if (isUser.getPhone() != null && userEdit.getPhone() == null) { //DB has a phone, delete from DB.
            Long id = isUser.getPhone().getId();
            deletePhoneFromUser(isUser.getId());
            phoneService.deleteById(id);
        }

        userEdit.setId(isUser.getId());
        userEdit.setPassword(isUser.getPassword());
        userEdit.setCreateAccountTime(isUser.getCreateAccountTime());
        userRepository.save(userEdit);
        return new UserToUserResponse().apply(userEdit);
    }

    private void deleteAddressFromUser(Long userId) {
        userRepository.deleteAddressByUserID(userId);
    }

    private void deletePhoneFromUser(Long userId) {
        userRepository.deletePhoneByUserId(userId);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserToUserResponse().apply(user))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by id [" + id + "] was not found");
                });
//        return new UserToUserResponse().apply(user);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by email [" + email + "] was not found");
                });
        return new UserToUserResponse().apply(user);
    }

}
