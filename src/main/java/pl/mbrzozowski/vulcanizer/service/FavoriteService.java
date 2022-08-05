package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.repository.FavoritesRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationFavorites;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoritesRepository favoritesRepository;

    public Favorites save(Favorites favorites) {
        ValidationFavorites.validBeforeSave(favorites);
        Favorites newFavorite = new Favorites(favorites.getUser(), favorites.getBusiness());
        return favoritesRepository.save(newFavorite);
    }

    public Favorites update(Favorites favorites) {
        return findById(favorites.getId()); //Cant edit favorites
    }

    public List<Favorites> findAll() {
        return favoritesRepository.findAll();
    }

    public Favorites findById(Long id) {
        return favoritesRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found favorite by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        favoritesRepository.deleteById(id);
    }
}
