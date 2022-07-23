package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.FavoritesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService implements ServiceLayer<Favorites, Favorites, Favorites> {
    private final FavoritesRepository favoritesRepository;

    @Override
    public Favorites save(Favorites favorites) {
        if (favorites.getUser() == null && favorites.getBusiness() == null) {
            throw new IllegalArgumentException("Can not add favorite without user and business");
        }
        Favorites newFavorite = new Favorites(favorites.getUser(), favorites.getBusiness());
        return favoritesRepository.save(newFavorite);
    }

    @Override
    public Favorites update(Favorites favorites) {
        if (favorites.getUser() == null && favorites.getBusiness() == null) {
            throw new IllegalArgumentException("user and business can not be null");
        }
        if (favorites.getId() == null) {
            throw new IllegalArgumentException("Favorite id can not be null");
        }
        Favorites favorites1 = findById(favorites.getId());
        favorites1.setBusiness(favorites.getBusiness());
        favorites1.setUser(favorites.getUser());
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
