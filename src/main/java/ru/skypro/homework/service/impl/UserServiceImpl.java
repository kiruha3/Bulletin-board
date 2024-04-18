package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import java.util.Optional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getUser(String username) {

        return userRepository.findUserByUsername(username);//.orElseThrow(UserNotFoundException::new);
        //// TODO не понятна ошибка java: cannot find symbol
        //symbol:   method orElseThrow(UserNotFou[...]::new)
        //location: class ru.skypro.homework.entity.User
    }
}
