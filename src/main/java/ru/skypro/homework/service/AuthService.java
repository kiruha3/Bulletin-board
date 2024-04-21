package ru.skypro.homework.service;

import ru.skypro.homework.dto.user.RegisterDto;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterDto registerDto);
}
