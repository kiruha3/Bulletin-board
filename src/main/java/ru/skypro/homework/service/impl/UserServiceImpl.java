package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final PasswordEncoder encoder;
    @Override
    public UserDto getUserDto(String userName) {
        return userMapper.userToUserDto(getUser(userName));
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void registerUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public byte[] getUserImage(int id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (user.getImage() == null) {
            throw new ImageNotFoundException();
        }

        return imageService.getImageData(user.getImage().getId());
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto body, Authentication authentication) {
        User user = getUser(authentication.getName());
        User infoToUpdate = userMapper.updateUserDtoToUser(body);

        user.setFirstName(infoToUpdate.getFirstName());
        user.setLastName(infoToUpdate.getLastName());
        user.setPhone(infoToUpdate.getPhone());
        return userMapper.updateUserToUserDto(userRepository.save(user));
    }

    @Override
    public void updateNewPassword(NewPasswordDto body, Authentication authentication) {
        User user = getUser(authentication.getName());
        User infoToUpdate = userMapper.updateNewPasswordDtoToUser(body);

        user.setPassword(encoder.encode(infoToUpdate.getPassword()));
        userRepository.save(user);
    }
}
