package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.repository.PhotoRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPhoto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final ValidationPhoto validationPhoto = new ValidationPhoto();

    public Photo save(Photo photo) {
        photo.setId(null);
        validationPhoto.accept(photo);
        return photoRepository.save(photo);
    }

    public Photo update(Photo photo) {
        validationPhoto.accept(photo);
        Photo photoUpdate = findById(photo.getId());
        photoUpdate.setUrl(photo.getUrl());
        photoRepository.save(photoUpdate);
        return photoUpdate;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Photo findById(Long id) {
        return photoRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found photo by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        photoRepository.deleteById(id);
    }

    public Photo findByUserId(Long userId) {
        return photoRepository
                .findByUserId(userId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found photo for User id [%s]", userId));
                });
    }
}
