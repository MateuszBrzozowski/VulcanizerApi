package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.FavoritesRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.FavoritesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService implements ServiceLayer<FavoritesRequest, Favorites, Favorites> {
    private final FavoritesRepository favoritesRepository;
    private final UserService userService;
    private final BusinessService businessService;

    @Override
    public Favorites save(FavoritesRequest favoritesRequest) {
        User user = userService.findById(favoritesRequest.getUserId());
        Business business = businessService.findById(favoritesRequest.getBusinessId());
        Favorites newFavorite = new Favorites(user, business);
        return favoritesRepository.save(newFavorite);
    }

    @Override
    public Favorites update(FavoritesRequest favoritesRequest) {
        if (favoritesRequest.getUserId() == null && favoritesRequest.getBusinessId() == null) {
            throw new IllegalArgumentException("user id and business id can not be null");
        }
        if (favoritesRequest.getId() == null) {
            throw new IllegalArgumentException("Favorite id can not be null");
        }
        Favorites favorites = findById(favoritesRequest.getId());
        User user = userService.findById(favoritesRequest.getUserId());
        Business business = businessService.findById(favoritesRequest.getBusinessId());
        favorites.setBusiness(business);
        favorites.setUser(user);
        return favoritesRepository.save(favorites);
    }

    @Override
    public List<Favorites> findAll() {
        return favoritesRepository.findAll();
    }

    @Override
    public Favorites findById(Long id) {
        return favoritesRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found favorite by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        favoritesRepository.deleteById(id);
    }
}
