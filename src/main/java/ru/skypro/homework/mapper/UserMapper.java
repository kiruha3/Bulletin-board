package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.ImageService;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    ImageService imageService;

    @Mapping(target = "image", expression = "java(imageService.getUserImageUrl(user.getId()))")
    public abstract UserDto userToUserDto(User user);
}
