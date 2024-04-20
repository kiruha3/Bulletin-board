package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.mapper.RegisterMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.AuthoritiesService;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final RegisterMapper registerMapper;
    private final UserService userService;
    private final AuthoritiesService authoritiesService;

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        ru.skypro.homework.entity.User user = registerMapper.toEntity(registerDto);

        if (manager.userExists(registerDto.getUsername())) {
            return false;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        userService.registerUser(user);
        authoritiesService.addAuthorities(user);

        return true;
    }

}
