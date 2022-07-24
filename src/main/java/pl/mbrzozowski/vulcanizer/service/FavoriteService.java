package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.FavoritesRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationFavorites;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService implements ServiceLayer<Favorites, Favorites, Favorites> {
    private final FavoritesRepository favoritesRepository;

    @Override
    public Favorites save(Favorites favorites) {
        ValidationFavorites.validBeforeSave(favorites);
        Favorites newFavorite = new Favorites(favorites.getUser(), favorites.getBusiness());
        return favoritesRepository.save(newFavorite);
    }

    @Override
    public Favorites update(Favorites favorites) {
        ValidationFavorites.validBeforeEdit(favorites);
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
