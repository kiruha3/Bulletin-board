package ru.skypro.homework.service;

import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    UserDto getUserDto(String userName);

    User getUser(String username);

    void registerUser(User user);

    byte[] getUserImage(int id);
}
