package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.PhotoRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.Optional;

import static org.mockito.Mockito.*;

class PhotoServiceTest {
    private PhotoService photoService;
    PhotoRepository photoRepository;

    @BeforeEach
    public void beforeEach() {
        photoRepository = mock(PhotoRepository.class);
        photoService = new PhotoService(photoRepository);
    }

    @Test
    void save_Success() {
        Photo photo = new Photo("url");
        photoService.save(photo);
        verify(photoRepository).save(photo);
    }

    @Test
    void save_NullUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo();
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.save(photo));
    }

    @Test
    void save_EmptyUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.save(photo));
    }

    @Test
    void save_ToLongUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo(new StringGenerator().apply(401));
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.save(photo));
    }

    @Test
    void save_MaxLengthUrl_DoesNotThrow() {
        Photo photo = new Photo(new StringGenerator().apply(400));
        Assertions.assertDoesNotThrow(() -> photoService.save(photo));
    }

    @Test
    void update_Success() {
        Photo photo = new Photo(1L, "url");
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        photoService.update(photo);
        verify(photoRepository).save(photo);
    }

    @Test
    void update_NullUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo(1L, null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.update(photo));
    }

    @Test
    void update_EmptyUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo(1L, "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.update(photo));
    }

    @Test
    void update_ToLongUrl_ThrowIllegalArgumentException() {
        Photo photo = new Photo(1L, new StringGenerator().apply(401));
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.update(photo));
    }

    @Test
    void update_MaxLengthUrl_ThrowDoesNotThrow() {
        Photo photo = new Photo(1L, new StringGenerator().apply(400));
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        Assertions.assertDoesNotThrow(() -> photoService.update(photo));
    }

    @Test
    void findById_NotFound_ThrowNoSuchElementException() {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.findById(1L));
    }

    @Test
    void findById_Found_DoesNotThrow() {
        Photo photo = new Photo(1L, "url");
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        Assertions.assertDoesNotThrow(() -> photoService.findById(1L));
    }

    @Test
    void findById_nullID_ThrowIllegalArgumentException() {
        when(photoRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> photoService.findById(null));
    }

    @Test
    void deletedByID_nullID_DoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> photoService.deleteById(null));
    }


}