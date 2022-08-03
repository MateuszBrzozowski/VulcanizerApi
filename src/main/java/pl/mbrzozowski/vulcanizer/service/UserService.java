package pl.mbrzozowski.vulcanizer.service;

import pl.mbrzozowski.vulcanizer.dto.*;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.TokenCheckSum;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.ArrayList;
import java.util.Optional;

interface UserService extends ServiceLayer<UserRequest, UserResponse, User> {

    Optional<User> findByEmail(String email);

    boolean saveFavorite(FavoritesRequest favoritesRequest);

    boolean deleteFavorite(Long userId, Long businessId);

    boolean isBusinessFavoriteForUser(Long userId, Long businessId);

    ArrayList<Business> findAllFavoriteForUser(Long userId);

    UserResponse register(UserRegisterBody userRegisterBody);

    User login(UserLoginBody userLoginBody);

    void resetPasswordStart(UserResetPasswordBody userResetPasswordBody);

    void resetPasswordSave(UserResetPasswordBody userResetPasswordBody);

    void setNewPassword(User user, String newPassword);

    TokenCheckSumResponse generateCheckSum(User user, String jwtToken);

    boolean isValidToken(User user, String token, String checkSumId, String checkSumProperties);
}
