package ru.skypro.homework.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepository repository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void testUploadImage() throws IOException {
        // подготовка данных
        long id = 1L;
        MultipartFile imageFile = mock(MultipartFile.class);
        when(imageFile.getOriginalFilename()).thenReturn("test.jpg");
        when(imageFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[10]));
        when(imageFile.getSize()).thenReturn(10L);
        when(imageFile.getContentType()).thenReturn("image/jpeg");
        when(imageFile.getBytes()).thenReturn(new byte[10]);

        // вызов метода uploadImage
        Image image = imageService.uploadImage(id, imageFile);

        // проверка взаимодействий и результатов
        verify(repository).save(image);
        assertEquals("listing_1.jpg", image.getFilePath());
        assertEquals(10L, image.getFileSize());
        assertEquals("image/jpeg", image.getMediaType());
        Assertions.assertArrayEquals(new byte[10], image.getData());
    }

    @Test
    public void testRemoveImage() {
        // подготовка данных
        Image image = mock(Image.class);

        // вызов метода removeImage
        imageService.removeImage(image);

        // проверка взаимодействия
        verify(repository).delete(image);
    }

    @Test
    public void testGetImage() {
        // подготовка данных
        Long id = 1L;
        Image image = mock(Image.class);
        when(repository.findById(id)).thenReturn(Optional.of(image));

        // вызов метода getImage
        Image result = imageService.getImage(id);

        // проверка результата
        assertEquals(image, result);
    }

    @Test
    public void testGetImageNotFound() {
        // подготовка данных
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // вызов метода getImage
        Image result = imageService.getImage(id);

        // проверка результата
        assertNotNull(result);
    }


}
