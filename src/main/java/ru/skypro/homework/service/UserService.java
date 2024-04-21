package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

public interface UserService {
    UserDto getUserDto(String userName);

    User getUser(String username);

    void registerUser(User user, Role role);

    byte[] getUserImage(int id);

    UpdateUserDto updateUser(UpdateUserDto body, Authentication authentication);

    void updateNewPassword(NewPasswordDto body, Authentication authentication);

    void uploadImage(MultipartFile image, Authentication authentication);
}
