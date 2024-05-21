package ru.skypro.homework.serviceImpl;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.user.UpdateUser;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AvatarService;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AvatarService avatarService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void setPassword_CorrectPassword_SuccessfullyUpdated() {
        NewPassword newPassword = new NewPassword("currentPassword", "newPassword");
        User user = new User();
        user.setPassword(passwordEncoder.encode("currentPassword"));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).save(any(User.class));

        userService.setPassword(newPassword, mock(Authentication.class));

        verify(passwordEncoder).matches("currentPassword", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void setPassword_IncorrectPassword_ThrowException() {
        NewPassword newPassword = new NewPassword("incorrectPassword", "newPassword");
        User user = new User();
        user.setPassword(passwordEncoder.encode("currentPassword"));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("incorrectPassword", user.getPassword())).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> userService.setPassword(newPassword, mock(Authentication.class)));

        verify(passwordEncoder).matches("incorrectPassword", user.getPassword());
        verify(userRepository, never()).save(user);
    }

    @Test
    public void getUser_ReturnUserDTO() {
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);

        UserDTO result = userService.getUser(authentication);

        assertEquals(userDTO, result);
    }

    @Test
    public void updateUserInfo_SuccessfullyUpdated() {
        UpdateUser update = new UpdateUser("Misha", "Voyteh", "123456789");
        User user = new User();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UpdateUser result = userService.updateUserInfo(update, mock(Authentication.class));

        assertEquals(update, result);
        verify(userRepository).save(user);
    }

    @Test
    public void updateUserAvatar_SuccessfullyUpdated() throws IOException {
        MultipartFile mockImage = mock(MultipartFile.class);
        User user = new User();
        user.setAvatar(new Avatar());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(avatarService.uploadAvatar(mockImage)).thenReturn(new Avatar());

        userService.updateUserAvatar(mockImage, mock(Authentication.class));

        verify(avatarService).removeAvatar(user.getAvatar());
        verify(userRepository).save(user);
    }

}