package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.FavoritesRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class FavoriteServiceTest {
    private FavoriteService favoriteService;
    private FavoritesRepository favoritesRepository;

    @BeforeEach
    public void beforeEach() {
        favoritesRepository = mock(FavoritesRepository.class);
        favoriteService = new FavoriteService(favoritesRepository);
    }

    @Test
    void save_Success() {
        User user = new User();
        Business business = new Business();
        Favorites favorites = new Favorites(user, business);
        favoriteService.save(favorites);
        verify(favoritesRepository).save(favorites);
    }

    @Test
    void save_UserNull_ThrowException() {
        Business business = new Business();
        Favorites favorites = new Favorites(null, business);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteService.save(favorites));
    }

    @Test
    void save_BusinessNull_ThrowException() {
        User user = new User();
        Favorites favorites = new Favorites(user, null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteService.save(favorites));
    }

    @Test
    void save_BusinessAndUserNull_ThrowException() {
        Favorites favorites = new Favorites(null, null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteService.save(favorites));
    }
}