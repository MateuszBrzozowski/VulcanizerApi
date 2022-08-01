package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Favorites;

public class ValidationFavorites {

    public static void validBeforeSave(Favorites favorites) {
        validUserAndBusiness(favorites);
    }

    public static void validBeforeEdit(Favorites favorites) {
        if (favorites.getId() == null) {
            throw new IllegalArgumentException("Favorite id can not be null");
        }
        validUserAndBusiness(favorites);
    }

    private static void validUserAndBusiness(Favorites favorites) {
        if (favorites.getUser() == null || favorites.getBusiness() == null) {
            throw new IllegalArgumentException("Can not add favorite without user and business");
        }
    }
}
