package ru.skypro.homework.serviceImpl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AvatarServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @Captor
    private ArgumentCaptor<Avatar> avatarCaptor;

    @Test
    public void testRemoveAvatar() {
        // Создание тестовых данных
        Avatar avatar = new Avatar();

        // Выполнение метода, который тестируется
        avatarService.removeAvatar(avatar);

        // Проверка, что метод delete был вызван с правильным аргументом
        verify(avatarRepository).delete(avatar);
    }

    @Test
    public void testGetAvatar() {
        // Создание тестовых данных
        Long avatarId = 1L;
        Avatar expectedAvatar = new Avatar();
        when(avatarRepository.findById(avatarId)).thenReturn(Optional.of(expectedAvatar));

        // Выполнение метода, который тестируется
        Avatar actualAvatar = avatarService.getAvatar(avatarId);

        // Проверка, что метод findById был вызван с правильным аргументом
        verify(avatarRepository).findById(avatarId);

        // Проверка, что возвращенный результат соответствует ожидаемому
        assertEquals(expectedAvatar, actualAvatar);
    }

    @Test
    public void testUploadAvatar() throws IOException {
        // Создание тестовых данных
        User user = new User();
        user.setId(1L);
        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("image.jpg");
        when(image.getContentType()).thenReturn("image/jpeg");
        when(image.getSize()).thenReturn(1024L);
        when(image.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[1024]));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("test@example.com");

        Path filePath = Path.of(AvatarServiceImpl.uploadDirectory, "user_1.jpg");

        // Выполнение метода, который тестируется
        Avatar avatar = avatarService.uploadAvatar(image);

        // Проверка, что методы создания директории и удаления файла были вызваны
        verify(Files.class);
        Files.createDirectories(filePath.getParent());
        verify(Files.class);
        Files.deleteIfExists(filePath);

        // Проверка, что методы InputStream и OutputStream были вызваны
        verify(image).getInputStream();
        verify(Files.class);
        Files.newOutputStream(eq(filePath), eq(CREATE_NEW));

        // Проверка, что метод save был вызван с правильным аргументом
        verify(avatarRepository).save(avatarCaptor.capture());

        // Проверка, что сохраненный объект Avatar содержит правильные данные
        Avatar savedAvatar = avatarCaptor.getValue();
        assertEquals(filePath.toString(), savedAvatar.getFilePath());
        assertEquals(1024L, savedAvatar.getFileSize());
        assertEquals("image/jpeg", savedAvatar.getMediaType());

        // Очистка контекста безопасности после выполнения теста
        SecurityContextHolder.clearContext();
    }



}
