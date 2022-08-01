package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Photo;

import java.util.function.Consumer;

public class ValidationPhoto implements Consumer<Photo> {

    @Override
    public void accept(Photo photo) {
        if (photo.getUrl() == null) {
            throw new IllegalArgumentException("Url can not be null");
        } else {
            if (photo.getUrl().equalsIgnoreCase("")) {
                throw new IllegalArgumentException("Url can not be empty");
            }
            if (photo.getUrl().length() > 400) {
                throw new IllegalArgumentException("Url to Long");
            }
        }
    }
}
