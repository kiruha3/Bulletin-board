package ru.skypro.homework.service;

import ru.skypro.homework.entity.User;

public interface UserService {

    User getUser(String username);

    void registerUser(User user);
}
