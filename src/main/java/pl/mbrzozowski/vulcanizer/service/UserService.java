package pl.mbrzozowski.vulcanizer.service;

import pl.mbrzozowski.vulcanizer.dto.*;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface UserService {

    Optional<User> findByEmail(String email);

    boolean saveFavorite(FavoritesRequest favoritesRequest);

    boolean deleteFavorite(Long userId, Long businessId);

    boolean isBusinessFavoriteForUser(Long userId, Long businessId);

    ArrayList<Company> findAllFavoriteForUser(Long userId);

    UserResponse register(UserRegisterBody userRegisterBody);

    User login(UserLoginBody userLoginBody);

    void resetPasswordStart(UserResetPasswordBody userResetPasswordBody);

    void resetPasswordSave(UserResetPasswordBody userResetPasswordBody);

    void setNewPassword(User user, String newPassword);

    UserResponse update(User user, UserRequest userRequest);

    boolean isUserOwner(User user);

    TokenCheckSumResponse generateCheckSum(User user, String jwtToken);

    boolean isValidToken(User user, String token, String checkSumId, String checkSumProperties);

    UserResponse saveAddress(User user, AddressRequest addressRequest);

    List<UserCompanyBranchResponse> findAllCompanyBranchForUser(User user);

    List<UserCompanyResponse> findAllCompanyForUser(User user);
}
