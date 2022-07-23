package pl.mbrzozowski.vulcanizer.service;

import pl.mbrzozowski.vulcanizer.dto.FavoritesRequest;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserService extends ServiceLayer<UserRequest, UserResponse, User> {

    Optional<User> findByEmail(String email);

    boolean saveFavorite(FavoritesRequest favoritesRequest);

    boolean deleteFavorite(Long userId, Long businessId);

    boolean isBusinessFavoriteForUser(Long userId, Long businessId);

    ArrayList<Business> findAllFavoriteForUser(Long userId);
}
